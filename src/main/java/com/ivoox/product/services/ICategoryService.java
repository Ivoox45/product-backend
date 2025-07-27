package com.ivoox.product.services;

import com.ivoox.product.dto.category.response.CategoryResponseDto;
import java.util.List;

public interface ICategoryService {
    CategoryResponseDto getCategoryById(Long id);

    List<CategoryResponseDto> getAllCategories();
}