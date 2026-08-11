package com.trendnest.trendnest_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer discount;
    private String imageUrl;
    private Integer stock;

    @Column(nullable = false)
    private Boolean active;

    // to build the relation between products and category
    // many product is mapped with a single category
    @ManyToOne
    // it will tell hybernate to create a foreign key to connect in the product table.
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
