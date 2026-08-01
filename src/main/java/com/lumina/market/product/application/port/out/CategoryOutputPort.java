package com.lumina.market.product.application.port.out;

import com.lumina.market.product.domain.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryOutputPort {

    Category save(Category category);

    Optional<Category> findById(Long id);

    List<Category> findAll();

    void deleteById(Long id);

    boolean existsByName(String name);
}