package com.liamfer.CloudCart.repository;

import com.liamfer.CloudCart.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity,Long> {
}
