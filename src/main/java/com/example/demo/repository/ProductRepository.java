package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContaining(String name, Pageable pageable);

    @Query(value = "select p.* from product p inner join shop s on p.shop_id = s.id where s.id = :shopId", nativeQuery = true)
    Page<Product> findProductsByShopId(@Param("shopId") Long shopId, Pageable pageable);

    @Query(value = "select p.* from product p inner join shop s on p.shop_id = s.id where s.id = :shopId and p.name like %:nameProduct%", nativeQuery = true)
    Page<Product> findProductsByShopIdAndProductName(@Param("shopId") Long shopId, @Param("nameProduct") String nameProduct,Pageable pageable);

    @Query(value = "select p.shop_id from product p where p.id = :productId", nativeQuery = true)
    Long findShopIdFromProduct(@Param("productId") Long productId);

    @Query(value = "select p.* from product p inner join category_product cp on p.id = cp.product_id where cp.category_id in (:categoryIds) group by p.id having count(p.id) = :numberOfCategoryIds", nativeQuery = true)
    Page<Product> findProductsByCategoryIds(@Param("categoryIds") List<Long> categoryIds,@Param("numberOfCategoryIds")int numberOfCategoryIds, Pageable pageable);

    @Query(value = "select p.* from product p inner join category_product cp on p.id = cp.product_id where cp.category_id in (:categoryIds) and p.name like %:nameProduct% group by p.id having count(p.id) = :numberOfCategoryIds", nativeQuery = true)
    Page<Product> findProductsByNameAndCategoryIds(@Param("categoryIds") List<Long> categoryIds, @Param("numberOfCategoryIds")int numberOfCategoryIds, @Param("nameProduct") String nameProduct, Pageable pageable);
}