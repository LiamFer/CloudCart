package com.liamfer.CloudCart.service;

import com.liamfer.CloudCart.dto.image.ImageResponseDTO;
import com.liamfer.CloudCart.dto.product.ProductDTO;
import com.liamfer.CloudCart.dto.product.ProductResponseDTO;
import com.liamfer.CloudCart.dto.product.ProductSimpleDTO;
import com.liamfer.CloudCart.entity.ImageEntity;
import com.liamfer.CloudCart.entity.ProductEntity;
import com.liamfer.CloudCart.repository.ImageRepository;
import com.liamfer.CloudCart.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    public ProductService(ProductRepository productRepository, ImageRepository imageRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
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

    public List<ImageResponseDTO> uploadProductImages(Long productID,List<MultipartFile> files){
        ProductEntity product = this.findProduct(productID);
        List<ImageEntity> images = files.stream().map(file -> new ImageEntity(cloudinaryService.addImage(file),product)).toList();
        return imageRepository.saveAll(images).stream().map(image -> modelMapper.map(image,ImageResponseDTO.class)).toList();
    }


    private ProductEntity findProduct(Long id){
        Optional<ProductEntity> product = productRepository.findById(id);
        if(product.isPresent()) return product.get();
        throw new EntityNotFoundException("Resource not Found");
    }
}
