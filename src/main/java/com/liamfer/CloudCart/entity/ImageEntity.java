package com.liamfer.CloudCart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_image_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public ImageEntity(String url, ProductEntity product) {
        this.url = url;
        this.product = product;
    }
}
