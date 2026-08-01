package com.lumina.market.product.presentation.response;

import com.lumina.market.product.application.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private Boolean isActive;
    private CategoryResponse category;
    private BrandResponse brand;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse fromDTO(ProductDTO dto) {
        return ProductResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .isActive(dto.getIsActive())
                .category(dto.getCategory() != null ? CategoryResponse.fromDTO(dto.getCategory()) : null)
                .brand(dto.getBrand() != null ? BrandResponse.fromDTO(dto.getBrand()) : null)
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}