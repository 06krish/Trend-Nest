package com.trendnest.trendnest_backend.service.impl;

import com.trendnest.trendnest_backend.dto.LoginRequestDTO;
import com.trendnest.trendnest_backend.dto.LoginResponseDTO;
import com.trendnest.trendnest_backend.dto.UserRequestDTO;
import com.trendnest.trendnest_backend.dto.UserResponseDTO;
import com.trendnest.trendnest_backend.entity.Role;
import com.trendnest.trendnest_backend.entity.User;
import com.trendnest.trendnest_backend.repository.UserRepository;
import com.trendnest.trendnest_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO register(UserRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                // encode password ( basically here we applied hashing)
                .password(passwordEncoder.encode(request.getPassword()))
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
    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("Invalid Email or Password"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid Email or Password");
        }

        return LoginResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}