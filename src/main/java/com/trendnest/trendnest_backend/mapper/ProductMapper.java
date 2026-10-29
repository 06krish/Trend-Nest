
package com.trendnest.trendnest_backend.mapper;

import com.trendnest.trendnest_backend.dto.ProductResponseDTO;
import com.trendnest.trendnest_backend.dto.ProductVariantResponseDTO;
import com.trendnest.trendnest_backend.entity.Product;
import com.trendnest.trendnest_backend.entity.ProductVariant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductResponseDTO toResponseDTO(Product product) {

        List<ProductVariantResponseDTO> variants = product.getVariants()
                .stream()
                .map(this::toVariantResponseDTO)
                .toList();

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .imageUrl(product.getImageUrl())
                .active(product.getActive())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .variants(variants)
                .build();
    }

    private ProductVariantResponseDTO toVariantResponseDTO(ProductVariant variant) {
        return ProductVariantResponseDTO.builder()
                .id(variant.getId())
                .productId(variant.getProduct().getId())
                .size(variant.getSize())
                .color(variant.getColor())
                .stock(variant.getStock())
                .sku(variant.getSku())
                .build();
    }
}
