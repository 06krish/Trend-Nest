package com.trendnest.trendnest_backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDTO {
    @NotBlank(message = "Product name is required")
    private String name;
    private String description;

    @NotBlank(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price greater than 0")
    private BigDecimal price;

    @Min(value = 0, message = "Discount cannot be negative")
    @Max(value = 100, message = "Discount cannot exceed 100")
    private Integer discount;

    private String ImageUrl;
    @NotBlank(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;

    private Boolean active;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
