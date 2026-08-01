package com.lumina.market.product.application.service;

import com.lumina.market.product.application.dto.CategoryDTO;
import com.lumina.market.product.application.dto.CreateCategoryRequest;
import com.lumina.market.product.application.port.in.CategoryInputPort;
import com.lumina.market.product.application.port.out.CategoryOutputPort;
import com.lumina.market.product.domain.model.Category;
import com.lumina.market.shared.exception.BusinessException;
import com.lumina.market.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryApplicationService implements CategoryInputPort {

    private final CategoryOutputPort categoryOutputPort;

    @Override
    @Transactional
    public CategoryDTO createCategory(CreateCategoryRequest request) {
        // Regla de negocio: No permitir categorías duplicadas
        if (categoryOutputPort.existsByName(request.getName())) {
            throw new BusinessException("Category already exists: " + request.getName());
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Category savedCategory = categoryOutputPort.save(category);
        return CategoryDTO.fromDomain(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryOutputPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return CategoryDTO.fromDomain(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryOutputPort.findAll().stream()
                .map(CategoryDTO::fromDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryOutputPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Category", "id", id);
        }
        categoryOutputPort.deleteById(id);
    }
}