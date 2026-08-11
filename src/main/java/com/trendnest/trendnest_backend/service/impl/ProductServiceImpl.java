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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public ProductResponseDTO patchProduct(Long id, ProductRequestDTO request) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }
}
