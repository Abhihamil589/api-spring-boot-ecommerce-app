package com.abhihamil.ecommerce.service;

import com.abhihamil.ecommerce.exceptions.ResourceNotFoundException;
import com.abhihamil.ecommerce.model.Category;
import com.abhihamil.ecommerce.payload.CategoryDTO;
import com.abhihamil.ecommerce.payload.CategoryResponse;
import com.abhihamil.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();

        /*
        categories.forEach(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryName(category.getCategoryName());
            categoryDTO.setCategoryId(category.getCategoryId());
            categoryDTOList.add(categoryDTO);
        });
        */

        categoryResponse.setCategories(categoryDTOList);

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

        return categoryRepository.findById(categoryId).stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .map(category1 -> {
                    category1.setCategoryName(categoryDTO.getCategoryName());
                    return modelMapper.map(categoryRepository.save(category1), CategoryDTO.class);
                }).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    }
}