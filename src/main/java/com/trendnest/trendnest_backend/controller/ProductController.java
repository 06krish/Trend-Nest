package com.trendnest.trendnest_backend.controller;

import com.trendnest.trendnest_backend.dto.ProductRequestDTO;
import com.trendnest.trendnest_backend.dto.ProductResponseDTO;
import com.trendnest.trendnest_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
// it automatically inject dependency that is constructor injection
@RequiredArgsConstructor
public class ProductController {
    // controller -> Interface -> Implementation
    // this is the idea behind the loose coupling.
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
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO request) {

        ProductResponseDTO response =
                productService.updateProduct(id, request);

        return ResponseEntity.ok(response);
    }
    // it only change the provided field.(Specific)
    // put  -> it update the entire field.
    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO>patchProduct(
            @PathVariable Long id,
            @Valid @RequestBody Map<String , Object> updates){
        ProductResponseDTO response = productService.patchProduct(id,updates);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProducts(

            @RequestParam(required = false)
            String name,

            @RequestParam(required = false)
            Long categoryId,

            @RequestParam(required = false)
            BigDecimal minPrice,

            @RequestParam(required = false)
            BigDecimal maxPrice,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id,asc")
            String sort) {

        Page<ProductResponseDTO> response =
                productService.searchProducts(
                        name,
                        categoryId,
                        minPrice,
                        maxPrice,
                        page,
                        size,
                        sort
                );

        return ResponseEntity.ok(response);
    }

}
