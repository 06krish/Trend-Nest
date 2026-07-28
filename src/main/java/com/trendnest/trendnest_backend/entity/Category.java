package com.trendnest.trendnest_backend.entity;

import jakarta.persistence.*;
import lombok.*;
// entity -> java class that directly represents a database table.
@Entity // it tells hibernate that this java class will be stored as a table in database
@Table(name = "categories") // Specifies the table name.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Builder is a Lombok annotation that automatically creates a Builder Pattern for your class.
//Instead of creating an object using constructors or setters, you can build it step by step.
// we will use builder in service layer
// it makes object creation easier. lombok annotation
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean active = true;
}