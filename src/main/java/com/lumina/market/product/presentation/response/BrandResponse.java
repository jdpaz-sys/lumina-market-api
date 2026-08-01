package com.lumina.market.product.presentation.response;

import com.lumina.market.product.application.dto.BrandDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {

    private Long id;
    private String name;
    private String logoUrl;

    public static BrandResponse fromDTO(BrandDTO dto){
        return BrandResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .logoUrl(dto.getLogoUrl())
                .build();
    }
}
