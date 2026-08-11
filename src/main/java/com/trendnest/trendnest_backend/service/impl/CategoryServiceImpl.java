package com.trendnest.trendnest_backend.service.impl;

import com.trendnest.trendnest_backend.dto.CategoryRequestDTO;
import com.trendnest.trendnest_backend.dto.CategoryResponseDTO;
import com.trendnest.trendnest_backend.entity.Category;
import com.trendnest.trendnest_backend.exception.CategoryAlreadyExistsException;
import com.trendnest.trendnest_backend.exception.ResourceNotFoundException;
import com.trendnest.trendnest_backend.mapper.CategoryMapper;
import com.trendnest.trendnest_backend.repository.CategoryRepository;
import com.trendnest.trendnest_backend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
// this annotation denotes that in this class contain business logics
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
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
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category not found " + id));
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .active(category.getActive())
                .build();
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO request) {
        Category category  = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("category not found " + id));
        Optional<Category> ExistingCategory = categoryRepository.findByName(request.getName());
        if(ExistingCategory.isPresent() && !ExistingCategory.get().getId().equals(id)){
            throw new CategoryAlreadyExistsException("Category already exists " +  request.getName() );
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());

        categoryRepository.save(category);
        return categoryMapper.toResponseDTO(category);
    }

    @Override
    public void deleteCategory(Long id) {
        // it returns optional value
        Category category  = categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("category not found " + id));
        categoryRepository.delete(category);

    }
    @Override
    public CategoryResponseDTO patchCategory(
            Long id,
            Map<String, Object> updates) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found " + id));

        // Update name
        if (updates.containsKey("name")) {

            String newName = (String) updates.get("name");

            Optional<Category> existingCategory =
                    categoryRepository.findByName(newName);

            if (existingCategory.isPresent()
                    && !existingCategory.get().getId().equals(id)) {

                throw new CategoryAlreadyExistsException(
                        "Category already exists: " + newName);
            }

            category.setName(newName);
        }

        // Update description
        if (updates.containsKey("description")) {
            category.setDescription(
                    (String) updates.get("description"));
        }

        // Update image
        if (updates.containsKey("imageUrl")) {
            category.setImageUrl(
                    (String) updates.get("imageUrl"));
        }

        categoryRepository.save(category);

        return categoryMapper.toResponseDTO(category);
    }
}