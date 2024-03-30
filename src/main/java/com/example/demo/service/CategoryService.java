package com.example.demo.service;

import com.example.demo.exception.CategoryNotFoundException;
import com.example.demo.model.dto.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.model.request.CategoryRequest;
import com.example.demo.model.response.ResponseApi;
import com.example.demo.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<ResponseApi<?>> createCategory(CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        categoryRepository.save(category);
        return new ResponseEntity<>(new ResponseApi<>("Create category success", 201), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseApi<Page<CategoryDto>>> getListCategories(String name, int page, int limit) {
        Page<Category> categoryInPage;
        Pageable pageable = PageRequest.of(page, limit);
        if (name != null) {
            categoryInPage = categoryRepository.findByNameContaining(name, pageable);
        } else {
            categoryInPage = categoryRepository.findAll(pageable);
        }
        Page<CategoryDto> categoryDtoInPage = categoryInPage.map(category -> modelMapper.map(category, CategoryDto.class));
        return new ResponseEntity<>(new ResponseApi<>("Get list categories success", 200, categoryDtoInPage), HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi<?>> updateCategory(Long id, CategoryRequest categoryRequest) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) throw new CategoryNotFoundException(String.format("CategoryID#%s does not exist",id));
        Category category = optionalCategory.get();
        modelMapper.map(categoryRequest, category);
        categoryRepository.save(category);
        return new ResponseEntity<>(new ResponseApi<>("Update category success", 200), HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi<?>> deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) throw new CategoryNotFoundException(String.format("CategoryID#%s does not exist",id));
        Category category = optionalCategory.get();
        category.setHasDelete(true);
        categoryRepository.save(category);
        return new ResponseEntity<>(new ResponseApi<>("Delete category success", 200), HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi<CategoryDto>> getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) throw new CategoryNotFoundException(String.format("CategoryID#%s does not exist",id));
        Category category = optionalCategory.get();
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        return new ResponseEntity<>(new ResponseApi<>("Get category success", 200, categoryDto), HttpStatus.OK);
    }
}
