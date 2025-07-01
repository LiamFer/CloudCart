package com.liamfer.CloudCart.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "cart_item")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private Integer quantity;

    public CartItemEntity(CartEntity cart, ProductEntity product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }
}