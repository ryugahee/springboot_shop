package com.example.shop.repository;

import com.example.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository 상속
public interface CartRepository extends JpaRepository<Cart, Long> {
}
