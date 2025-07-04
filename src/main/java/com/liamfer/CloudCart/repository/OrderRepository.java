package com.liamfer.CloudCart.repository;

import com.liamfer.CloudCart.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    Page<OrderEntity> findAllByUserId(String id, Pageable pageable);
}
