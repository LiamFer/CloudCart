package com.liamfer.CloudCart.service;

import com.liamfer.CloudCart.dto.product.CreateProductDTO;
import com.liamfer.CloudCart.dto.product.CreatedProduct;
import com.liamfer.CloudCart.entity.ProductEntity;
import com.liamfer.CloudCart.mapper.ProductMapper;
import com.liamfer.CloudCart.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public CreatedProduct createNewProduct(CreateProductDTO createProductDTO){
        System.out.println(createProductDTO);
        ProductEntity product = productMapper.toProductEntity(createProductDTO);
        System.out.println(product);
        return productMapper.toCreatedProduct(productRepository.save(product));
    }
}
