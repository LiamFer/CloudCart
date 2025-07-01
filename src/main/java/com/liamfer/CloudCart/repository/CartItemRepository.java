package com.liamfer.CloudCart.repository;

import com.liamfer.CloudCart.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {
    Optional<CartItemEntity> findByIdAndCartId(Long itemID,Long cartID);
}
