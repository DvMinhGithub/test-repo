package com.example.demo.controller;

import com.example.demo.model.dto.ProductDto;
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

import com.example.demo.model.request.ProductRequest;
import com.example.demo.service.ProductService;

import java.security.Principal;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create product", description = "Create product")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/create")
    public ResponseEntity<ResponseApi<?>> createProduct(Principal principal, @RequestBody ProductRequest productRequest) {
        return productService.createProduct(principal, productRequest);
    }

    @Operation(summary = "Get list products", description = "Get list products")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/get")
    public ResponseEntity<ResponseApi<Page<ProductDto>>> getListProducts(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "limit", defaultValue = "20", required = false) int limit) {
        return productService.getListProducts(name, page, limit);
    }

    @Operation(summary = "Update product", description = "Update product")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseApi<?>> updateProduct(Principal principal, @PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(principal, id, productRequest);
    }

    @Operation(summary = "Delete product", description = "Delete product")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseApi<?>> deleteProduct(Principal principal, @PathVariable Long id) {
        return productService.deleteProduct(principal, id);
    }

    @Operation(summary = "Get product by id", description = "Get product by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<ProductDto>> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }


    @Operation(summary = "Get list products from shop", description = "Get list product from shop")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/getFromShop/{id}")
    public ResponseEntity<ResponseApi<Page<ProductDto>>> getListProductsFromShop(@PathVariable("id") Long id, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "limit", defaultValue = "20", required = false) int limit) {
        return productService.getListProductsFromShop(id, name, page, limit);
    }

    @Operation(summary = "Get list product from category", description = "Get list product from category")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/getFromCategories")
    public ResponseEntity<ResponseApi<Page<ProductDto>>> getListProductsFromCategories(@RequestParam(value = "ids", required = false) String categoryIds, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "limit", defaultValue = "20", required = false) int limit) {
        return productService.getListProductsFromCategories(categoryIds, name, page, limit);
    }

    @Operation(summary = "Approve product", description = "Approve product")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/approved/{id}")
    public ResponseEntity<ResponseApi<?>> approvedProduct(@PathVariable("id") Long id) {
        return productService.approvedProduct(id);
    }
}
