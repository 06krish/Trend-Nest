package com.trendnest.trendnest_backend.dto;
import lombok.*;
import com.trendnest.trendnest_backend.entity.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Role role;
    private Boolean active;
}