package com.trendnest.trendnest_backend.service;

import com.trendnest.trendnest_backend.dto.ProductRequestDTO;
import com.trendnest.trendnest_backend.dto.ProductResponseDTO;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO request);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO request);

    ProductResponseDTO patchProduct(Long id, Map<String, Object> updates);

    void deleteProduct(Long id);
}