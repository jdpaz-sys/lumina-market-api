package com.lumina.market.product.application.port.in;

import com.lumina.market.product.application.dto.CategoryDTO;
import com.lumina.market.product.application.dto.CreateCategoryRequest;
import java.util.List;

public interface CategoryInputPort {

    CategoryDTO createCategory(CreateCategoryRequest request);

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getAllCategories();

    void deleteCategory(Long id);
}