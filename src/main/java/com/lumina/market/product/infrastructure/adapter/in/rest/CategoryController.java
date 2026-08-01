package com.lumina.market.product.infrastructure.adapter.in.rest;

import com.lumina.market.product.application.dto.CreateCategoryRequest;
import com.lumina.market.product.application.port.in.CategoryInputPort;
import com.lumina.market.product.domain.model.Category;
import com.lumina.market.product.presentation.response.CategoryResponse;
import com.lumina.market.shared.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryInputPort categoryInputPort;

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CreateCategoryRequest request){
        var dto = categoryInputPort.createCategory(request);
        var response = CategoryResponse.fromDTO(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response, "Category created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(
            @PathVariable Long id){
        var dto = categoryInputPort.getCategoryById(id);
        var response = CategoryResponse.fromDTO(dto);

        return ResponseEntity.ok(ApiResponse.success(response, "Category retrieved successfully"));

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories(){
        var dtos = categoryInputPort.getAllCategories();
        var responses = dtos.stream()
                .map(CategoryResponse::fromDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses, "Categories retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategoryById(@PathVariable Long id){
        categoryInputPort.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Category deleted successfully"));
    }
}
