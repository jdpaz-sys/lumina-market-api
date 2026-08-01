package com.lumina.market.product.application.dto;

import com.lumina.market.product.domain.model.Product;
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
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private Boolean isActive;
    private CategoryDTO category;
    private BrandDTO brand;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductDTO fromDomain(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .isActive(product.getIsActive())
                .category(CategoryDTO.fromDomain(product.getCategory()))
                .brand(BrandDTO.fromDomain(product.getBrand()))
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public Product toDomain() {
        return Product.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .imageUrl(this.imageUrl)
                .isActive(this.isActive)
                .category(this.category != null ? this.category.toDomain() : null)
                .brand(this.brand != null ? this.brand.toDomain() : null)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}