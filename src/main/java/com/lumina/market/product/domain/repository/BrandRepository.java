package com.lumina.market.product.domain.repository;

import com.lumina.market.product.domain.model.Brand;
import java.util.List;
import java.util.Optional;

public interface BrandRepository {

    Brand save(Brand brand);

    Optional<Brand> findById(Long id);

    List<Brand> findAll();

    void deleteById(Long id);

    boolean existsByName(String name);
}