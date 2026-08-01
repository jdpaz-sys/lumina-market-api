package com.lumina.market.product.infrastructure.adapter.out.persistence;

import com.lumina.market.product.infrastructure.adapter.out.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);
}