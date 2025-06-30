package com.liamfer.CloudCart.mapper;

import com.liamfer.CloudCart.dto.product.ProductDTO;
import com.liamfer.CloudCart.dto.product.ProductResponseDTO;
import com.liamfer.CloudCart.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toProductEntity(ProductDTO product);
    ProductResponseDTO toProductResponseDTO(ProductEntity product);
}
