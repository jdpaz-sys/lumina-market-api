package com.lumina.market.product.infrastructure.adapter.in.rest;

import com.lumina.market.product.application.dto.CreateProductRequest;
import com.lumina.market.product.application.dto.UpdateProductRequest;
import com.lumina.market.product.application.port.in.ProductInputPort;
import com.lumina.market.product.presentation.response.ProductResponse;
import com.lumina.market.shared.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductInputPort productInputPort;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @Valid @RequestBody CreateProductRequest request) {
        var dto = productInputPort.createProduct(request);
        var response = ProductResponse.fromDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Product created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        var dto = productInputPort.getProductById(id);
        var response = ProductResponse.fromDTO(dto);
        return ResponseEntity.ok(ApiResponse.success(response, "Product retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        var dtos = productInputPort.getAllProducts();
        var responses = dtos.stream()
                .map(ProductResponse::fromDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses, "Products retrieved successfully"));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByCategory(
            @PathVariable Long categoryId) {
        var dtos = productInputPort.getProductsByCategory(categoryId);
        var responses = dtos.stream()
                .map(ProductResponse::fromDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses,
                "Products retrieved successfully by category"));
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByBrand(
            @PathVariable Long brandId) {
        var dtos = productInputPort.getProductsByBrand(brandId);
        var responses = dtos.stream()
                .map(ProductResponse::fromDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses,
                "Products retrieved successfully by brand"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request) {
        var dto = productInputPort.updateProduct(id, request);
        var response = ProductResponse.fromDTO(dto);
        return ResponseEntity.ok(ApiResponse.success(response, "Product updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productInputPort.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Product deleted successfully"));
    }
}