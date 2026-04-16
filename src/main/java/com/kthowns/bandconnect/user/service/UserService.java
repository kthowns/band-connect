package com.kthowns.bandconnect.user.service;

import com.kthowns.bandconnect.common.exception.CustomResponseCode;
import com.kthowns.bandconnect.common.exception.CustomException;
import com.kthowns.bandconnect.user.dto.SignupRequest;
import com.kthowns.bandconnect.user.entity.User;
import com.kthowns.bandconnect.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(CustomResponseCode.DUPLICATED_USERNAME);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(CustomResponseCode.DUPLICATED_EMAIL);
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }
}
