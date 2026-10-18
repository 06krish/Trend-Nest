package com.trendnest.trendnest_backend.controller;

import com.trendnest.trendnest_backend.dto.CartItemRequestDTO;
import com.trendnest.trendnest_backend.dto.CartResponseDTO;
import com.trendnest.trendnest_backend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO>addItemToCart(@Valid @RequestBody CartItemRequestDTO requestDTO){
        CartResponseDTO response =  cartService.addItemToCart(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getAllCartItems(){
        CartResponseDTO response = cartService.getMyCart();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponseDTO>updateCartItem(@PathVariable Long cartItemId,@RequestParam Integer quantity){
        CartResponseDTO response = cartService.updateCartItem(cartItemId,quantity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponseDTO>removeCartItem(@PathVariable Long cartItemId){
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping()
    public ResponseEntity<CartResponseDTO>removeAllCartItems(){
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }

}
