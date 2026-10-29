
package com.trendnest.trendnest_backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Price must be greater than 0"
    )
    private BigDecimal price;

    @Min(value = 0, message = "Discount cannot be negative")
    @Max(value = 100, message = "Discount cannot exceed 100")
    private Integer discount;

    private String imageUrl;

    @NotNull(message = "Active status is required")
    private Boolean active;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @Valid
    private List<ProductVariantRequestDTO> variants;
}
