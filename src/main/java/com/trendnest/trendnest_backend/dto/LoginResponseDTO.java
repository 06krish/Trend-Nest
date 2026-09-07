package com.trendnest.trendnest_backend.dto;

import com.trendnest.trendnest_backend.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String token;
}
