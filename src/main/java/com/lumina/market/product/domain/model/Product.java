package com.lumina.market.product.domain.model;

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
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private Boolean isActive;
    private Category category; //Relación Categoria
    private Brand brand;    //Relació Brand
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
