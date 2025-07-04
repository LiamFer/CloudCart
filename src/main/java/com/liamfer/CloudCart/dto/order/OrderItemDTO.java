package com.liamfer.CloudCart.dto.order;

import com.liamfer.CloudCart.dto.product.ProductSimpleDTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private ProductSimpleDTO product;
    private Integer quantity;
    private Double price;
}
