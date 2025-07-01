package com.liamfer.CloudCart.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String description;

    @Positive
    @NotNull
    private Double price;

    @PositiveOrZero
    @NotNull
    private Integer stock;

    @NotNull
    private Boolean available;
}