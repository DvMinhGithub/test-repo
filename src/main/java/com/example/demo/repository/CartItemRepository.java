package com.example.demo.repository;

import com.example.demo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = "select ci.* from cart_item ci where ci.user_id = :userId", nativeQuery = true)
    List<CartItem> findByUserId(@Param("userId")Long userId);
}
