package com.trendnest.trendnest_backend.controller;

import com.trendnest.trendnest_backend.dto.CategoryRequestDTO;
import com.trendnest.trendnest_backend.dto.CategoryResponseDTO;
import com.trendnest.trendnest_backend.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // this handles http request and return json response.
@RequestMapping("/api/categories")
public class CategoryController {
    // used constructor injection
    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }
    // requestbody converts json body into java object.
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO request) {
        CategoryResponseDTO categoryresponseDTO = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryresponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {

        List<CategoryResponseDTO> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
        return  ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO requestDTO) {

        return ResponseEntity.ok(categoryService.updateCategory(id, requestDTO));
    }
}
