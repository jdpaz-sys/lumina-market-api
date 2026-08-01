package com.lumina.market.product.application.port.out;

import com.lumina.market.product.domain.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductOutputPort {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByBrandId(Long brandId);

    void deleteById(Long id);
}