package com.trendnest.trendnest_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponseDTO {

    private Long id;

    private Long productId;

    private String productName;

    private BigDecimal price;

    private BigDecimal discount;

    private Integer quantity;

    private BigDecimal itemTotal;
}