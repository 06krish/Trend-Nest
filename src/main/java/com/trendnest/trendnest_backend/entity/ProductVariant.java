
package com.trendnest.trendnest_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "product_variants",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"product_id", "size", "color"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each variant belongs to one product
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Clothing size: S, M, L, XL, etc.
    @Column(nullable = false, length = 20)
    private String size;

    // Colour: Black, White, Blue, etc.
    @Column(nullable = false, length = 50)
    private String color;

    // Stock for this specific size and colour
    @Column(nullable = false)
    private Integer stock;

    // Unique identifier for inventory management
    @Column(unique = true, length = 100)
    private String sku;
}
