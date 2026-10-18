package com.trendnest.trendnest_backend.repository;

import com.trendnest.trendnest_backend.entity.Cart;
import com.trendnest.trendnest_backend.entity.CartItem;
import com.trendnest.trendnest_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}