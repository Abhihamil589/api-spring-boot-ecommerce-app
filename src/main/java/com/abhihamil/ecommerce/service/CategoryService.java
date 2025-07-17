package com.abhihamil.ecommerce.service;

import com.abhihamil.ecommerce.payload.CategoryDTO;
import com.abhihamil.ecommerce.payload.CategoryResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface CategoryService {

    CategoryResponse getAllCategories();

    CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}