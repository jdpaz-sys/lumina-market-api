package com.lumina.market.product.application.service;

import com.lumina.market.product.application.dto.CreateProductRequest;
import com.lumina.market.product.application.dto.ProductDTO;
import com.lumina.market.product.application.dto.UpdateProductRequest;
import com.lumina.market.product.application.port.in.ProductInputPort;
import com.lumina.market.product.application.port.out.CategoryOutputPort;
import com.lumina.market.product.application.port.out.ProductOutputPort;
import com.lumina.market.product.application.port.out.BrandOutputPort;
import com.lumina.market.product.domain.model.Category;
import com.lumina.market.product.domain.model.Product;
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
public class ProductApplicationService implements ProductInputPort {

    private final ProductOutputPort productOutputPort;
    private final CategoryOutputPort categoryOutputPort;
    private final BrandOutputPort brandOutputPort;

    @Override
    @Transactional
    public ProductDTO createProduct(CreateProductRequest request) {
        // 1. Validar que la categoría exista
        Category category = categoryOutputPort.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        // 2. Validar que la marca exista
        Brand brand = brandOutputPort.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", request.getBrandId()));

        // 3. Validar stock no negativo
        if (request.getStock() != null && request.getStock() < 0) {
            throw new BusinessException("Stock cannot be negative");
        }

        // 4. Construir el Product con sus relaciones
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock() != null ? request.getStock() : 0)
                .imageUrl(request.getImageUrl())
                .isActive(true)
                .category(category)
                .brand(brand)
                .build();

        Product savedProduct = productOutputPort.save(product);
        return ProductDTO.fromDomain(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productOutputPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return ProductDTO.fromDomain(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productOutputPort.findAll().stream()
                .map(ProductDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        // Validar que la categoría exista
        if (!categoryOutputPort.findById(categoryId).isPresent()) {
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
        return productOutputPort.findByCategoryId(categoryId).stream()
                .map(ProductDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByBrand(Long brandId) {
        if (!brandOutputPort.findById(brandId).isPresent()) {
            throw new ResourceNotFoundException("Brand", "id", brandId);
        }
        return productOutputPort.findByBrandId(brandId).stream()
                .map(ProductDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, UpdateProductRequest request) {
        Product product = productOutputPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        // Actualizar campos solo si vienen en el request
        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getStock() != null) product.setStock(request.getStock());
        if (request.getImageUrl() != null) product.setImageUrl(request.getImageUrl());
        if (request.getIsActive() != null) product.setIsActive(request.getIsActive());

        // Actualizar categoría si viene
        if (request.getCategoryId() != null) {
            Category category = categoryOutputPort.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));
            product.setCategory(category);
        }

        // Actualizar marca si viene
        if (request.getBrandId() != null) {
            Brand brand = brandOutputPort.findById(request.getBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", request.getBrandId()));
            product.setBrand(brand);
        }

        Product updatedProduct = productOutputPort.save(product);
        return ProductDTO.fromDomain(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productOutputPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        productOutputPort.deleteById(id);
    }
}