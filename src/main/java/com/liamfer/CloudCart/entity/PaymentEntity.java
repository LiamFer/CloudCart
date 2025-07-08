package com.liamfer.CloudCart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String stripePaymentId;

    @Column(nullable = false)
    private String status;

    private String paymentMethod;

    @Column(nullable = false)
    private Double amount;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;
}