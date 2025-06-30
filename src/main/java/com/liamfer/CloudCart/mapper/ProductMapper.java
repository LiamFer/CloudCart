package com.liamfer.CloudCart.mapper;

import com.liamfer.CloudCart.dto.product.CreateProductDTO;
import com.liamfer.CloudCart.dto.product.CreatedProduct;
import com.liamfer.CloudCart.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    CreateProductDTO toCreateProductDTO(ProductEntity product);
    ProductEntity toProductEntity(CreateProductDTO product);
    CreatedProduct toCreatedProduct(ProductEntity product);
}
