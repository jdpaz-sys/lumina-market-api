package com.lumina.market.product.infrastructure.adapter.in.rest;

import com.lumina.market.product.application.dto.CreateBrandRequest;
import com.lumina.market.product.application.port.in.BrandInputPort;
import com.lumina.market.product.presentation.response.BrandResponse;
import com.lumina.market.shared.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandInputPort brandInputPort;

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(
            @Valid @RequestBody CreateBrandRequest request) {
        var dto = brandInputPort.createBrand(request);
        var response = BrandResponse.fromDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Brand created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandById(@PathVariable Long id) {
        var dto = brandInputPort.getBrandById(id);
        var response = BrandResponse.fromDTO(dto);
        return ResponseEntity.ok(ApiResponse.success(response, "Brand retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrands() {
        var dtos = brandInputPort.getAllBrands();
        var responses = dtos.stream()
                .map(BrandResponse::fromDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses, "Brands retrieved successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Long id) {
        brandInputPort.deleteBrand(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Brand deleted successfully"));
    }
}