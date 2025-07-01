package com.liamfer.CloudCart.repository;

import com.liamfer.CloudCart.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity,Long> {
    Optional<CartEntity> findByUserId(String id);
}
