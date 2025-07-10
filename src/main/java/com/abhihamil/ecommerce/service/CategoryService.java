package com.abhihamil.ecommerce.service;

import com.abhihamil.ecommerce.model.Category;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    void createCategory(@RequestBody Category category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);
}