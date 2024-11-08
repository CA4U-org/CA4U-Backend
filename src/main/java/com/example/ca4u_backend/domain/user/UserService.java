package com.example.ca4u_backend.domain.user;

import com.example.ca4u_backend.domain.user.dto.UpdateStudentInfoRequest;
import com.example.ca4u_backend.domain.user.dto.UserResponseDto;
import com.example.ca4u_backend.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getUser(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new BaseException("해당 사용자가 없습니다."));
        return UserResponseDto.from(findUser);
    }

    @Transactional
    public void updateUser(Long userId, UpdateStudentInfoRequest requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException("해당 사용자가 없습니다."));
        user.updateStudentInfo(requestDto.getName(), requestDto.getDepartment(), requestDto.getMajor());
    }
}
