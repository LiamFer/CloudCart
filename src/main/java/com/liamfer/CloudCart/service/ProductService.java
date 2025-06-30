package com.liamfer.CloudCart.service;

import com.liamfer.CloudCart.dto.product.ProductDTO;
import com.liamfer.CloudCart.dto.product.ProductResponseDTO;
import com.liamfer.CloudCart.dto.product.ProductSimpleDTO;
import com.liamfer.CloudCart.entity.ProductEntity;
import com.liamfer.CloudCart.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public Page<ProductSimpleDTO> findAllProducts(Pageable pageable){
        return productRepository.findAll(pageable).map(product -> modelMapper.map(product,ProductSimpleDTO.class));
    }

    public ProductResponseDTO createNewProduct(ProductDTO productDTO){
        ProductEntity product = modelMapper.map(productDTO,ProductEntity.class);
        return modelMapper.map(productRepository.save(product), ProductResponseDTO.class);
    }

    public ProductResponseDTO updateProduct(Long id, ProductDTO updateProduct){
        ProductEntity product = this.findProduct(id);
        product.setName(updateProduct.getName());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());
        product.setStock(updateProduct.getStock());
        return modelMapper.map(productRepository.save(product), ProductResponseDTO.class);
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
