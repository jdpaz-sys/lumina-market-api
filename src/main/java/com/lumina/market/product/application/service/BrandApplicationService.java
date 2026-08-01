package com.lumina.market.product.application.service;

import com.lumina.market.product.application.dto.BrandDTO;
import com.lumina.market.product.application.dto.CreateBrandRequest;
import com.lumina.market.product.application.port.in.BrandInputPort;
import com.lumina.market.product.application.port.out.BrandOutputPort;
import com.lumina.market.product.domain.model.Brand;
import com.lumina.market.shared.exception.BusinessException;
import com.lumina.market.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandApplicationService implements BrandInputPort {

    private final BrandOutputPort brandOutputPort;

    @Override
    @Transactional
    public BrandDTO createBrand(CreateBrandRequest request) {
        if (brandOutputPort.existsByName(request.getName())) {
            throw new BusinessException("Brand already exists: " + request.getName());
        }

        Brand brand = Brand.builder()
                .name(request.getName())
                .logoUrl(request.getLogoUrl())
                .build();

        Brand savedBrand = brandOutputPort.save(brand);
        return BrandDTO.fromDomain(savedBrand);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandDTO getBrandById(Long id) {
        Brand brand = brandOutputPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", id));
        return BrandDTO.fromDomain(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandDTO> getAllBrands() {
        return brandOutputPort.findAll().stream()
                .map(BrandDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteBrand(Long id) {
        if (!brandOutputPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Brand", "id", id);
        }
        brandOutputPort.deleteById(id);
    }
}