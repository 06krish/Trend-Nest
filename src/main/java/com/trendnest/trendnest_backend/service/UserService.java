package com.trendnest.trendnest_backend.service;

import com.trendnest.trendnest_backend.dto.UserRequestDTO;
import com.trendnest.trendnest_backend.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO register(UserRequestDTO request);
}