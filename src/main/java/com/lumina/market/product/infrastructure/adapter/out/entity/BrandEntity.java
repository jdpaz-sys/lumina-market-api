package com.lumina.market.product.infrastructure.adapter.out.entity;

import com.lumina.market.product.domain.model.Brand;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    // Convertir Domain -> Entity
    public static BrandEntity fromDomain(Brand brand) {
        return BrandEntity.builder()
                .id(brand.getId())
                .name(brand.getName())
                .logoUrl(brand.getLogoUrl())
                .build();
    }

    // Convertir Entity -> Domain
    public Brand toDomain() {
        return Brand.builder()
                .id(this.id)
                .name(this.name)
                .logoUrl(this.logoUrl)
                .build();
    }
}