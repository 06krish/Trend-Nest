package com.trendnest.trendnest_backend.service;

import com.trendnest.trendnest_backend.dto.CategoryResponseDTO;
import com.trendnest.trendnest_backend.dto.CategoryRequestDTO;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO request);

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Long id);

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO request);

    void deleteCategory(Long id);

    CategoryResponseDTO patchCategory(
            Long id,
            Map<String, Object> updates);
}