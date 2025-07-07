package com.liamfer.CloudCart.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemDTO {
    @Positive
    @NotNull
    private Long productID;
    @Positive
    @NotNull
    private Integer amount;
}
