package com.trendnest.trendnest_backend.service.impl;

import com.trendnest.trendnest_backend.dto.ProductRequestDTO;
import com.trendnest.trendnest_backend.dto.ProductResponseDTO;
import com.trendnest.trendnest_backend.entity.Category;
import com.trendnest.trendnest_backend.entity.Product;
import com.trendnest.trendnest_backend.exception.ResourceNotFoundException;
import com.trendnest.trendnest_backend.mapper.ProductMapper;
import com.trendnest.trendnest_backend.repository.CategoryRepository;
import com.trendnest.trendnest_backend.repository.ProductRepository;
import com.trendnest.trendnest_backend.service.ProductService;
import com.trendnest.trendnest_backend.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

// here we are using lombok that's why we dont need to write @Autowired on every injection
// we only have to write this @RequiredArgsConstructor and spring will automatically will inject these dependencies.
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request){
        Category category =  categoryRepository.findById(request.getCategoryId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Category Not Found"));
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .discount(request.getDiscount())
                .imageUrl(request.getImageUrl())
                .stock(request.getStock())
                .active(request.getActive())
                .category(category)
                .build();
        Product savedProduct =  productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                // foe each product it calls out mapper
                //productMapper.toResponseDTO(product)
                // we dont want to expose our entity directly thats why we use DTO convertor
                .map(productMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id));

        return productMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + request.getCategoryId()));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        product.setImageUrl(request.getImageUrl());
        product.setStock(request.getStock());
        product.setActive(request.getActive());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    public ProductResponseDTO patchProduct(Long id, Map<String, Object> updates) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id));

        updates.forEach((key, value) -> {

            switch (key) {

                case "name" -> product.setName((String) value);

                case "description" -> product.setDescription((String) value);

                case "price" -> product.setPrice(
                        new BigDecimal(value.toString()));

                case "discount" -> product.setDiscount(
                        Integer.valueOf(value.toString()));

                case "imageUrl" -> product.setImageUrl((String) value);

                case "stock" -> product.setStock(
                        Integer.valueOf(value.toString()));

                case "active" -> product.setActive(
                        (Boolean) value);

                case "categoryId" -> {
                    Long categoryId = Long.valueOf(value.toString());

                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Category not found with id: " + categoryId));

                    product.setCategory(category);
                }
            }
        });

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
    @Override
    public Page<ProductResponseDTO> searchProducts(
            String name,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size,
            String sort) {

        Specification<Product> specification =
                (root, query, criteriaBuilder) -> null;

        if (name != null && !name.isBlank()) {
            specification = specification.and(
                    ProductSpecifications.hasName(name)
            );
        }

        if (categoryId != null) {
            specification = specification.and(
                    ProductSpecifications.hasCategory(categoryId)
            );
        }

        if (minPrice != null) {
            specification = specification.and(
                    ProductSpecifications.priceGreaterThanOrEqualTo(minPrice)
            );
        }

        if (maxPrice != null) {
            specification = specification.and(
                    ProductSpecifications.priceLessThanOrEqualTo(maxPrice)
            );
        }

        String[] sortParts = sort.split(",");

        String sortField = sortParts[0];

        Sort.Direction direction =
                sortParts.length > 1 &&
                        sortParts[1].equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(direction, sortField)
                );

        Page<Product> products =
                productRepository.findAll(
                        specification,
                        pageable
                );

        return products.map(productMapper::toResponseDTO);
    }
}
