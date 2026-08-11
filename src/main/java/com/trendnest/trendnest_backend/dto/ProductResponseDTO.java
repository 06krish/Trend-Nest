package com.trendnest.trendnest_backend.dto;

import lombok.*;

import java.math.BigDecimal;
// DTO give us control over API
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer discount;

    private String imageUrl;

    private Integer stock;

    private Boolean active;

    private Long categoryId;

    private String categoryName;
}
