package com.trendnest.trendnest_backend.service;

import com.trendnest.trendnest_backend.dto.CartItemRequestDTO;
import com.trendnest.trendnest_backend.dto.CartResponseDTO;

public interface CartService {

    CartResponseDTO addItemToCart(
            CartItemRequestDTO requestDTO
    );

    CartResponseDTO getMyCart();

    CartResponseDTO updateCartItem(
            Long cartItemId,
            Integer quantity
    );

    void removeCartItem(
            Long cartItemId
    );

    void clearCart();
}