package com.trendnest.trendnest_backend.service.impl;
import com.trendnest.trendnest_backend.dto.CartItemRequestDTO;
import com.trendnest.trendnest_backend.dto.CartItemResponseDTO;
import com.trendnest.trendnest_backend.dto.CartResponseDTO;
import com.trendnest.trendnest_backend.entity.Cart;
import com.trendnest.trendnest_backend.entity.CartItem;
import com.trendnest.trendnest_backend.entity.Product;
import com.trendnest.trendnest_backend.entity.User;
import com.trendnest.trendnest_backend.exception.ResourceNotFoundException;
import com.trendnest.trendnest_backend.repository.CartItemRepository;
import com.trendnest.trendnest_backend.repository.CartRepository;
import com.trendnest.trendnest_backend.repository.ProductRepository;
import com.trendnest.trendnest_backend.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        return (User) authentication.getPrincipal();
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {

                    Cart cart = new Cart();
                    cart.setUser(user);

                    return cartRepository.save(cart);
                });
    }

    @Override
    public CartResponseDTO addItemToCart(
            CartItemRequestDTO requestDTO
    ) {
        User user = getLoggedInUser();
        Cart cart = getOrCreateCart(user);
        Product product = productRepository
                .findById(requestDTO.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: "
                                        + requestDTO.getProductId()
                        )
                );
        if (!Boolean.TRUE.equals(product.getActive())) {
            throw new IllegalArgumentException(
                    "This product is currently inactive"
            );
        }
        if (product.getStock() == null ||
                product.getStock() < requestDTO.getQuantity()) {
            throw new IllegalArgumentException(
                    "Insufficient product stock"
            );
        }
        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);
        if (cartItem != null) {
            int newQuantity =
                    cartItem.getQuantity()
                            + requestDTO.getQuantity();
            if (product.getStock() < newQuantity) {
                throw new IllegalArgumentException(
                        "Requested quantity exceeds available stock"
                );
            }
            cartItem.setQuantity(newQuantity);

        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(requestDTO.getQuantity());
        }
        cartItemRepository.save(cartItem);
        return getMyCart();
    }

    @Override
    public CartResponseDTO getMyCart() {
        User user = getLoggedInUser();
        Cart cart = getOrCreateCart(user);
        List<CartItemResponseDTO> itemResponses =
                cart.getItems()
                        .stream()
                        .map(this::mapToCartItemResponse)
                        .toList();
        BigDecimal totalAmount =
                itemResponses.stream()
                        .map(CartItemResponseDTO::getItemTotal)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );
        return new CartResponseDTO(
                cart.getId(),
                itemResponses,
                totalAmount
        );
    }

    @Override
    public CartResponseDTO updateCartItem(
            Long cartItemId,
            Integer quantity
    ) {
        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException(
                    "Quantity must be at least 1"
            );
        }

        User user = getLoggedInUser();

        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found with id: "
                                        + cartItemId
                        )
                );

        validateCartItemOwnership(cartItem, user);

        Product product = cartItem.getProduct();

        if (product.getStock() == null ||
                product.getStock() < quantity) {

            throw new IllegalArgumentException(
                    "Requested quantity exceeds available stock"
            );
        }

        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        return getMyCart();
    }

    @Override
    public void removeCartItem(Long cartItemId) {

        User user = getLoggedInUser();

        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart item not found with id: "
                                        + cartItemId
                        )
                );

        validateCartItemOwnership(cartItem, user);

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart() {

        User user = getLoggedInUser();

        Cart cart = getOrCreateCart(user);

        cart.getItems().clear();

        cartRepository.save(cart);
    }

    private void validateCartItemOwnership(
            CartItem cartItem,
            User user
    ) {

        Long cartUserId =
                cartItem.getCart()
                        .getUser()
                        .getId();

        if (!cartUserId.equals(user.getId())) {

            throw new IllegalArgumentException(
                    "You are not allowed to modify this cart item"
            );
        }
    }

    private CartItemResponseDTO mapToCartItemResponse(
            CartItem cartItem
    ) {

        Product product = cartItem.getProduct();

        BigDecimal price = product.getPrice();

        BigDecimal discount =
                product.getDiscount() == null
                        ? BigDecimal.ZERO
                        : BigDecimal.valueOf(
                        product.getDiscount()
                );

        BigDecimal discountAmount =
                price.multiply(discount)
                        .divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP
                        );

        BigDecimal discountedPrice =
                price.subtract(discountAmount);

        BigDecimal itemTotal =
                discountedPrice.multiply(
                        BigDecimal.valueOf(
                                cartItem.getQuantity()
                        )
                ).setScale(
                        2,
                        RoundingMode.HALF_UP
                );

        return new CartItemResponseDTO(
                cartItem.getId(),
                product.getId(),
                product.getName(),
                price,
                discount,
                cartItem.getQuantity(),
                itemTotal
        );
    }
}