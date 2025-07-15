package com.abhihamil.ecommerce.service;

import com.abhihamil.ecommerce.model.Category;
import com.abhihamil.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {

        categoryRepository.findById(categoryId).stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .ifPresentOrElse(category -> categoryRepository.delete(category), () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
                });

        return "Category " + categoryId + " deleted Successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        return categoryRepository.findById(categoryId).stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .map(category1 -> {
                    category1.setCategoryName(category.getCategoryName());
                    return categoryRepository.save(category1);
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }
}