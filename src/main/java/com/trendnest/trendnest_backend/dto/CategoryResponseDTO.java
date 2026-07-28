package com.trendnest.trendnest_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Boolean active;
}