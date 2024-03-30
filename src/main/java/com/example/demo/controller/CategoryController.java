package com.example.demo.controller;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.response.ResponseApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.request.CategoryRequest;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create category", description = "Create category")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    public ResponseEntity<ResponseApi<?>> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @Operation(summary = "Get list categories", description = "Get list categories")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/get")
    public ResponseEntity<ResponseApi<Page<CategoryDto>>> getListCategories(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "limit", defaultValue = "20", required = false) int limit) {
        return categoryService.getListCategories(name, page, limit);
    }

    @Operation(summary = "Update category", description = "Update category")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseApi<?>> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(id, categoryRequest);
    }

    @Operation(summary = "Delete category", description = "Delete category")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseApi<?>> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

    @Operation(summary = "Get category by id", description = "Get category by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<CategoryDto>> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
}
