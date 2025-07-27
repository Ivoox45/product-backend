package com.ivoox.product.mapper;

import com.ivoox.product.entities.Category;
import com.ivoox.product.dto.category.response.CategoryResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toCategoryResponseDto(Category category);
}
