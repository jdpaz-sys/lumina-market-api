package com.lumina.market.product.domain.repository;

import com.lumina.market.product.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByBrandId(Long brandId);

    void deleteById(Long id);
}