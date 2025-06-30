package com.liamfer.CloudCart.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    private Double price;

    @PositiveOrZero
    private Integer stock;
}