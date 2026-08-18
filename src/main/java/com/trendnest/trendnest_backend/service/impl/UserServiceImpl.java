package com.trendnest.trendnest_backend.service.impl;

import com.trendnest.trendnest_backend.dto.UserRequestDTO;
import com.trendnest.trendnest_backend.dto.UserResponseDTO;
import com.trendnest.trendnest_backend.entity.Role;
import com.trendnest.trendnest_backend.entity.User;
import com.trendnest.trendnest_backend.repository.UserRepository;
import com.trendnest.trendnest_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO register(UserRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.CUSTOMER)
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponseDTO.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .active(savedUser.getActive())
                .build();
    }
}