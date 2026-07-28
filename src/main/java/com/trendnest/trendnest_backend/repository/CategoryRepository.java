package com.trendnest.trendnest_backend.repository;
import com.trendnest.trendnest_backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// this interface is responsible for interacting with database.
// and responsible for database operations.
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    boolean existsByName(String name);
}
//Q: Why do we extend JpaRepository?
//Answer:
//Because JpaRepository provides ready-made CRUD operations, pagination, sorting, and
//other database functionalities. It reduces boilerplate code and
//lets us focus on business logic instead of writing SQL for
//common operations.
