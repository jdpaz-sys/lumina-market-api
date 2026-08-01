package com.lumina.market.product.application.dto;

import com.lumina.market.product.domain.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {

    private Long id;
    private String name;
    private String logoUrl;

    public static BrandDTO fromDomain(Brand brand) {
        return BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .logoUrl(brand.getLogoUrl())
                .build();
    }

    public Brand toDomain() {
        return Brand.builder()
                .id(this.id)
                .name(this.name)
                .logoUrl(this.logoUrl)
                .build();
    }
}