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
// Think of a Stream as a pipeline that lets you process every element one by one.
//
//Instead of writing loops, you write operations like map, filter, and sorted.
// tool all data from database in list then by using pipeline pass it one by one then map the data and made dto
// of entity data.
    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> CategoryResponseDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .imageUrl(category.getImageUrl())
                        .active(category.getActive())
                        .build())
                .toList();
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