package com.liamfer.CloudCart.repository;

import com.liamfer.CloudCart.entity.OrderEntity;
import com.liamfer.CloudCart.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    Page<OrderEntity> findAllByUserId(String id, Pageable pageable);
    Optional<OrderEntity> findByUserAndPaymentId(UserEntity user, Long id);
}
