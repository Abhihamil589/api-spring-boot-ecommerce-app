package com.abhihamil.ecommerce.service;

import com.abhihamil.ecommerce.exceptions.ResourceNotFoundException;
import com.abhihamil.ecommerce.model.Category;
import com.abhihamil.ecommerce.payload.CategoryDTO;
import com.abhihamil.ecommerce.payload.CategoryResponse;
import com.abhihamil.ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Category> pageCategories = categoryRepository.findAll(pageable);

        List<Category> categories = pageCategories.getContent();
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

        categoryResponse.setPageNumber(pageCategories.getNumber());
        categoryResponse.setPageSize(pageCategories.getSize());
        categoryResponse.setCategories(categoryDTOList);
        categoryResponse.setLastPage(pageCategories.isLast());
        categoryResponse.setTotalPages(pageCategories.getTotalPages());
        categoryResponse.setTotalElements(pageCategories.getTotalElements());

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