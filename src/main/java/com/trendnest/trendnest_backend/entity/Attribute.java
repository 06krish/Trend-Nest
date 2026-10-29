
package com.trendnest.trendnest_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "attributes",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"name"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;
}
