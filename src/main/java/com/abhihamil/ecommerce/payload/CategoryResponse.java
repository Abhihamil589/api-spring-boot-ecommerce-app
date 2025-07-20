package com.abhihamil.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> categories;
    private int pageNumber;
    private int pageSize;
    private boolean isLastPage;
    private long totalElements;
    private int totalPages;
}
