
package com.trendnest.trendnest_backend.repository;

import com.trendnest.trendnest_backend.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository
        extends JpaRepository<ProductVariant, Long> {

    // Find all variants of a product
    List<ProductVariant> findByProductId(Long productId);

    // Find variants of a product for a selected size
    List<ProductVariant> findByProductIdAndSizeIgnoreCase(
            Long productId,
            String size
    );

    // Find a specific product-size-colour combination
    Optional<ProductVariant> findByProductIdAndSizeIgnoreCaseAndColorIgnoreCase(
            Long productId,
            String size,
            String color
    );
}
