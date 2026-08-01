package com.lumina.market.product.application.port.in;

import com.lumina.market.product.application.dto.BrandDTO;
import com.lumina.market.product.application.dto.CreateBrandRequest;
import java.util.List;

public interface BrandInputPort {

    BrandDTO createBrand(CreateBrandRequest request);

    BrandDTO getBrandById(Long id);

    List<BrandDTO> getAllBrands();

    void deleteBrand(Long id);
}