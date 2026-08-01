package com.lumina.market.product.application.port.in;

import com.lumina.market.product.application.dto.CreateProductRequest;
import com.lumina.market.product.application.dto.ProductDTO;
import com.lumina.market.product.application.dto.UpdateProductRequest;
import java.util.List;

public interface ProductInputPort {

    ProductDTO createProduct(CreateProductRequest request);

    ProductDTO getProductById(Long id);

    List<ProductDTO> getAllProducts();

    List<ProductDTO> getProductsByCategory(Long categoryId);

    List<ProductDTO> getProductsByBrand(Long brandId);

    ProductDTO updateProduct(Long id, UpdateProductRequest request);

    void deleteProduct(Long id);
}