package com.trendnest.trendnest_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass
// this tells jpa that this is not a table .this is a parent class whose
// field should be inherited by entities.
public class BaseEntity {

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    // jpa lifecycle callback that executes before new entity inserted into the database.
    // commonly used to initilized fields cretedAt , updatedAt.
    // runs before the entity is inserted into the database.
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    //@PreUpdate executes before an existing entity is updated.
    // It's commonly used to update fields such as updatedAt.
    // runs whenever an existing record is updated.
    // only updatedAt changes automatically.
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}