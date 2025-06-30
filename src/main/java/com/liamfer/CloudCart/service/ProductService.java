package com.liamfer.CloudCart.service;

import com.liamfer.CloudCart.dto.product.ProductDTO;
import com.liamfer.CloudCart.dto.product.ProductResponseDTO;
import com.liamfer.CloudCart.entity.ProductEntity;
import com.liamfer.CloudCart.mapper.ProductMapper;
import com.liamfer.CloudCart.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDTO createNewProduct(ProductDTO productDTO){
        ProductEntity product = productMapper.toProductEntity(productDTO);
        return productMapper.toProductResponseDTO(productRepository.save(product));
    }

    public ProductResponseDTO updateProduct(Long id, ProductDTO updateProduct){
        ProductEntity product = this.findProduct(id);
        product.setName(updateProduct.getName());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());
        product.setStock(updateProduct.getStock());
        ProductEntity updatedProduct = productRepository.save(product);
        return productMapper.toProductResponseDTO(updatedProduct);
    }

    public void deleteProduct(Long id){
        this.findProduct(id);
        productRepository.deleteById(id);
    }


    private ProductEntity findProduct(Long id){
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isPresent()) return product.get();
        throw new EntityNotFoundException("Resource not Found");
    }
}
