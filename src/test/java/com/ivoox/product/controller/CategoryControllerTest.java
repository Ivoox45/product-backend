package com.ivoox.product.controller;

import com.ivoox.product.dto.category.response.CategoryResponseDto;
import com.ivoox.product.services.ICategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false) 
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ICategoryService categoryService;

    private List<CategoryResponseDto> mockCategories;

    @BeforeEach
    void setUp() {
        mockCategories = Arrays.asList(
                CategoryResponseDto.builder().id(1L).name("Libros").build(),
                CategoryResponseDto.builder().id(2L).name("Electrónica").build());
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        Mockito.when(categoryService.getAllCategories()).thenReturn(mockCategories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Libros")))
                .andExpect(jsonPath("$[1].name", is("Electrónica")));
    }

    @Test
    void shouldReturnCategoryById() throws Exception {
        CategoryResponseDto dto = CategoryResponseDto.builder().id(1L).name("Libros").build();
        Mockito.when(categoryService.getCategoryById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Libros")));
    }
}
