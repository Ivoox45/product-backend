package com.ivoox.product.services.impl;

import com.ivoox.product.dto.category.response.CategoryResponseDto;
import com.ivoox.product.entities.Category;
import com.ivoox.product.exception.NotFoundException;
import com.ivoox.product.mapper.CategoryMapper;
import com.ivoox.product.repository.CategoryRepository;
import com.ivoox.product.services.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  @Transactional(readOnly = true)
  public CategoryResponseDto getCategoryById(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    return categoryMapper.toCategoryResponseDto(category);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CategoryResponseDto> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(categoryMapper::toCategoryResponseDto)
        .collect(Collectors.toList());
  }
}
