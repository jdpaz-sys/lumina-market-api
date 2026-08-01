package com.lumina.market.product.infrastructure.adapter.out.persistence;

import com.lumina.market.product.application.port.out.BrandOutputPort;
import com.lumina.market.product.domain.model.Brand;
import com.lumina.market.product.infrastructure.adapter.out.entity.BrandEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandOutputPort {

    private final BrandJpaRepository brandJpaRepository;

    @Override
    public Brand save(Brand brand) {
        BrandEntity entity = BrandEntity.fromDomain(brand);
        BrandEntity savedEntity = brandJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return brandJpaRepository.findById(id).map(BrandEntity::toDomain);
    }

    @Override
    public List<Brand> findAll() {
        return brandJpaRepository.findAll().stream()
                .map(BrandEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        brandJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return brandJpaRepository.existsByName(name);
    }
}