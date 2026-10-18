package com.trendnest.trendnest_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartResponseDTO {

    private Long cartId;

    private List<CartItemResponseDTO> items;

    private BigDecimal totalAmount;
}
