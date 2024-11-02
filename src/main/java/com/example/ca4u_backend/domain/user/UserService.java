package com.example.ca4u_backend.domain.user;

import com.example.ca4u_backend.domain.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUser(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
        return UserResponseDto.from(findUser);
    }
}
