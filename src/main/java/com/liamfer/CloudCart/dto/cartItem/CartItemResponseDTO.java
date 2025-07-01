package com.liamfer.CloudCart.dto.cartItem;

import com.liamfer.CloudCart.dto.product.ProductSimpleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDTO {
    private Long id;
    private ProductSimpleDTO product;
    private Integer quantity;
}
