package com.liamfer.CloudCart.repository;

import com.liamfer.CloudCart.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity,Long> {
    Optional<CartEntity> findByUserId(String id);
}
