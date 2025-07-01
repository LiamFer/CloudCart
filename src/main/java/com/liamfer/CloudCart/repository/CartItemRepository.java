package com.liamfer.CloudCart.repository;

import com.liamfer.CloudCart.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {
}
