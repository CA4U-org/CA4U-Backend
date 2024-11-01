package com.example.ca4u_backend.config;

import com.example.ca4u_backend.domain.user.CustomOAuth2UserService;
import com.example.ca4u_backend.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .formLogin((login) -> login.disable())
                .httpBasic((basic) -> basic.disable())
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/"))
                .headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()))
                .oauth2Login((oauth2)->oauth2
                        .userInfoEndpoint(
                                userInfoEndPointConfig ->
                                        userInfoEndPointConfig.userService(customOAuth2UserService)
                        ))
                //oauth2Client를 추가 하게 되면 세부 로그인 로직들을 구현 해야 한다.
                .authorizeHttpRequests((auth) -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
