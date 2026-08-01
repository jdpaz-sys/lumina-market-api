package com.lumina.market.product.infrastructure.adapter.out.persistence;

import com.lumina.market.product.infrastructure.adapter.out.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandJpaRepository extends JpaRepository<BrandEntity, Long> {
    boolean existsByName(String name);
}