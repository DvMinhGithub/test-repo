package com.example.demo.service;

import java.security.Principal;
import java.util.*;

import com.example.demo.entity.ProductAttribute;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.ProductPermissionException;
import com.example.demo.exception.ShopNotFoundException;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductAttributeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Shop;
import com.example.demo.model.request.ProductRequest;
import com.example.demo.model.response.ResponseApi;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShopRepository;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ShopRepository shopRepository;

    private final ProductAttributeRepository productAttributeRepository;

    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ShopRepository shopRepository, ProductAttributeRepository productAttributeRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.shopRepository = shopRepository;
        this.productAttributeRepository = productAttributeRepository;
        this.modelMapper = modelMapper;
    }

    // Only seller can create product
    public ResponseEntity<ResponseApi<?>> createProduct(Principal principal, ProductRequest productRequest) {
        Shop shop = shopRepository.getShopFromUserEmail(principal.getName());
        if (shop == null) throw new ShopNotFoundException("Need create shop before create product");
        Product product = modelMapper.map(productRequest, Product.class);
        product.setShop(shop);
        Product savedProduct = productRepository.save(product);
        List<Category> listCategory = new ArrayList<>();

        for (Long categoryId : productRequest.getCategoryIds()) {
            Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                category.getListProduct().add(savedProduct);
                listCategory.add(category);
            }
        }
        categoryRepository.saveAll(listCategory);

        for (ProductRequest.ProductAttributeRequest productAttributeRequest : productRequest.getListAttribute()) {
            ProductAttribute productAttribute = modelMapper.map(productAttributeRequest, ProductAttribute.class);
            productAttribute.setProduct(savedProduct);
            productAttributeRepository.save(productAttribute);
        }
        return new ResponseEntity<>(new ResponseApi<>("Create product success", 201), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseApi<Page<ProductDto>>> getListProducts(String name, int page, int limit) {
        Page<Product> productInPage;
        Pageable pageable = PageRequest.of(page, limit);
        if (name != null) {
            productInPage = productRepository.findByNameContaining(name, pageable);
        } else {
            productInPage = productRepository.findAll(pageable);
        }
        Page<ProductDto> productDtoInPage = productInPage.map(product -> modelMapper.map(product, ProductDto.class));
        return new ResponseEntity<>(new ResponseApi<>("Get list products success", 200, productDtoInPage), HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi<?>> updateProduct(Principal principal, Long id, ProductRequest productRequest) {
        Shop shop = shopRepository.getShopFromUserEmail(principal.getName());
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) throw new ProductNotFoundException(String.format("ProductID#%s does not exist",id));
        Product product = optionalProduct.get();
        if (shop.getId().equals(product.getShop().getId()))
            throw new ProductPermissionException(String.format("UserID#%s does not have permission to operation ProductID#%s",shop.getUser().getId() ,id));
        categoryRepository.deleteCategoryProduct(product.getId());
        modelMapper.map(productRequest, product);
        productRepository.save(product);
        return new ResponseEntity<>(new ResponseApi<>("Update product success", 200), HttpStatus.OK);
    }

    // Only seller can delete product
    public ResponseEntity<ResponseApi<?>> deleteProduct(Principal principal, Long id) {
        Shop shop = shopRepository.getShopFromUserEmail(principal.getName());
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) throw new ProductNotFoundException(String.format("ProductID#%s does not exist",id));
        Product product = optionalProduct.get();
        if (shop.getId().equals(product.getShop().getId()))
            throw new ProductPermissionException(String.format("UserID#%s does not have permission to operation ProductID#%s",shop.getUser().getId() ,id));
        product.setHasDelete(true);
        productRepository.save(product);
        return new ResponseEntity<>(new ResponseApi<>("Delete product success", 200), HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi<ProductDto>> getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) throw new ProductNotFoundException(String.format("ProductID#%s does not exist",id));
        Product product = optionalProduct.get();
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return new ResponseEntity<>(new ResponseApi<>("Get product success", 200, productDto), HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi<Page<ProductDto>>> getListProductsFromShop(Long id, String name, int page, int limit) {
        Page<Product> productInPage;
        Pageable pageable = PageRequest.of(page, limit);
        if (name != null) {
            productInPage = productRepository.findProductsByShopIdAndProductName(id, name, pageable);
        } else {
            productInPage = productRepository.findProductsByShopId(id, pageable);
        }
        Page<ProductDto> productDtoInPage = productInPage.map(product -> modelMapper.map(product, ProductDto.class));
        return new ResponseEntity<>(new ResponseApi<>("Get product from shop success", 200, productDtoInPage), HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi<Page<ProductDto>>> getListProductsFromCategories(String categoryIds, String name, int page, int limit) {
        String[] arrCategoryIds = categoryIds.split(",");
        List<Long> listLongCategoryIds = new ArrayList<>();
        for (String categoryId : arrCategoryIds) {
            listLongCategoryIds.add(Long.parseLong(categoryId));
        }
        Page<Product> productInPage;
        Pageable pageable = PageRequest.of(page, limit);

        if (name != null) {
            productInPage = productRepository.findProductsByNameAndCategoryIds(listLongCategoryIds,
                    listLongCategoryIds.size(), name, pageable);
        } else {
            productInPage = productRepository.findProductsByCategoryIds(listLongCategoryIds,
                    listLongCategoryIds.size(), pageable);
        }
        Page<ProductDto> productDtoInPage = productInPage.map(product -> modelMapper.map(product, ProductDto.class));
        return new ResponseEntity<>(new ResponseApi<>("Get products success", 200, productDtoInPage), HttpStatus.OK);
    }

    public ResponseEntity<ResponseApi<?>> approvedProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) throw new ProductNotFoundException(String.format("ProductID#%s does not exist",id));
        Product product = optionalProduct.get();
        product.setHasApproved(true);
        productRepository.save(product);
        return new ResponseEntity<>(new ResponseApi<>("Approve product success", 200), HttpStatus.OK);
    }
}