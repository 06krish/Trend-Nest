package com.trendnest.trendnest_backend.controller;

import com.trendnest.trendnest_backend.dto.ProductRequestDTO;
import com.trendnest.trendnest_backend.dto.ProductResponseDTO;
import com.trendnest.trendnest_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
// it automatically inject dependency that is constructor injection
@RequiredArgsConstructor
public class ProductController {
    public final ProductService productService;
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO request){
         ProductResponseDTO res =  productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<ProductResponseDTO> res =  productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id){
        ProductResponseDTO res =  productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
