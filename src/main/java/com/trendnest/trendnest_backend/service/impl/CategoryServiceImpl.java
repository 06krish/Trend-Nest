package com.trendnest.trendnest_backend.service.impl;

import com.trendnest.trendnest_backend.dto.CategoryRequestDTO;
import com.trendnest.trendnest_backend.dto.CategoryResponseDTO;
import com.trendnest.trendnest_backend.entity.Category;
import com.trendnest.trendnest_backend.exception.CategoryAlreadyExistsException;
import com.trendnest.trendnest_backend.repository.CategoryRepository;
import com.trendnest.trendnest_backend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// this annotation denotes that in this class contain business logics
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {
        if(categoryRepository.existsByName(request.getName())){
            throw new CategoryAlreadyExistsException("Category already exists");
        }
        Category categuery = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .active(true)
                .build();
        Category savedCategory = categoryRepository.save(categuery);
        return CategoryResponseDTO.builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .description(savedCategory.getDescription())
                .imageUrl(savedCategory.getImageUrl())
                .active(savedCategory.getActive())
                .build();

    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return List.of();
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        return null;
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO request) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }
}