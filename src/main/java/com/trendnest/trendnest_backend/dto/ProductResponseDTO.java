
package com.trendnest.trendnest_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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
    private Boolean active;
    private Long categoryId;
    private String categoryName;

    private List<ProductVariantResponseDTO> variants;
}
