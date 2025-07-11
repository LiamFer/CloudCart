package com.liamfer.CloudCart.repository;

import com.liamfer.CloudCart.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {
    List<OrderItemEntity> findAllByProductId(Long id);
}
