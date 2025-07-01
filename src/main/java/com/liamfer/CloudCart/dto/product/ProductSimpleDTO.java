package com.liamfer.CloudCart.dto.product;


import com.liamfer.CloudCart.dto.image.ImageResponseDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductSimpleDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    private List<ImageResponseDTO> images;
}
