package com.trendnest.trendnest_backend.service;

import com.trendnest.trendnest_backend.dto.LoginRequestDTO;
import com.trendnest.trendnest_backend.dto.LoginResponseDTO;
import com.trendnest.trendnest_backend.dto.UserRequestDTO;
import com.trendnest.trendnest_backend.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO register(UserRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);
}