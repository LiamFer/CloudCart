package com.liamfer.CloudCart.dto.product;


import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductSimpleDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
