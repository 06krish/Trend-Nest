package com.trendnest.trendnest_backend.mapper;
import com.trendnest.trendnest_backend.dto.CategoryResponseDTO;
import com.trendnest.trendnest_backend.entity.Category;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;

@Component
public class CategoryMapper {
    public CategoryResponseDTO toResponseDTO(Category category){
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .active(category.getActive())
                .build();
    }

    public Category toEntity(CategoryResponseDTO responseDTO){
        return Category.builder()
                .id(responseDTO.getId())
                .name(responseDTO.getName())
                .description(responseDTO.getDescription())
                .imageUrl(responseDTO.getImageUrl())
                .active(responseDTO.getActive())
                .build();
    }
}

