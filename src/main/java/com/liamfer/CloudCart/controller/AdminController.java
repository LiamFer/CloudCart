package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.product.ProductDTO;
import com.liamfer.CloudCart.dto.product.ProductResponseDTO;
import com.liamfer.CloudCart.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;
    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> createNewProduct(@RequestBody @Valid ProductDTO newProduct){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createNewProduct(newProduct));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id,
                                                            @RequestBody @Valid ProductDTO product){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id,product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok().build();
    }

}
