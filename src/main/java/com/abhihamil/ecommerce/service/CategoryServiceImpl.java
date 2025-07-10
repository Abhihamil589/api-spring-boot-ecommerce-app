package com.abhihamil.ecommerce.service;

import com.abhihamil.ecommerce.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {

        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        optionalCategory.ifPresentOrElse(categories::remove, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        });

        return "Category " + categoryId + " deleted Successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        return categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .map(category1 -> {
                    category1.setCategoryName(category.getCategoryName());
                    return category1;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }
}