package com.abhihamil.ecommerce.controller;

import com.abhihamil.ecommerce.payload.CategoryDTO;
import com.abhihamil.ecommerce.payload.CategoryResponse;
import com.abhihamil.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.abhihamil.ecommerce.config.AppConstant.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNo", required = false, defaultValue = PAGE_NO) Integer pageNo,
            @RequestParam(name = "pageSize", required = false, defaultValue = PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = SORT_BY) String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = SORT_ORDER) String sortOrder) {

        CategoryResponse categories = categoryService.getAllCategories(pageNo, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {

        CategoryDTO categoryDTO = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);

    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {

        CategoryDTO updateCategory = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }
}
