
package com.trendnest.trendnest_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantResponseDTO {

    private Long id;
    private Long productId;
    private String size;
    private String color;
    private Integer stock;
    private String sku;
}
