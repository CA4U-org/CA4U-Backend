package com.example.ca4u_backend.domain.user;

import com.example.ca4u_backend.domain.user.dto.CustomOAuth2User;
import com.example.ca4u_backend.domain.user.dto.GoogleResponse;
import com.example.ca4u_backend.domain.user.dto.OAuth2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    //DB 저장을 진행 하기 위해 유저 래퍼지토리 주입
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // OAuth2 제공 업체로부터 사용자 정보를 얻어오는 과정
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        if (!registrationId.equals("google")) return null;

        OAuth2Response oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            user = User.builder()
                    .username(username)
                    .email(oAuth2Response.getEmail())
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
        } else {
            user.updateEmail(oAuth2Response.getEmail());
            userRepository.save(user);
        }

        return new CustomOAuth2User(user);
    }
}