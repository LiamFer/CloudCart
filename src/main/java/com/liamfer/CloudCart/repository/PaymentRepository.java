package com.liamfer.CloudCart.repository;

import com.liamfer.CloudCart.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity,Long> {
    Optional<PaymentEntity> findBystripePaymentId(String paymentId);
}
