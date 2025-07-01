package com.liamfer.CloudCart.dto.product;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Boolean available;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
