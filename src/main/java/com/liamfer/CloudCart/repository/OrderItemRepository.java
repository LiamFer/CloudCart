package com.liamfer.CloudCart.repository;

import com.liamfer.CloudCart.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {
}
