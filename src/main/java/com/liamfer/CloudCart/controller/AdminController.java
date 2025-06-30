package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.product.CreateProductDTO;
import com.liamfer.CloudCart.dto.product.CreatedProduct;
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
    public ResponseEntity<CreatedProduct> createNewProduct(@RequestBody @Valid CreateProductDTO newProduct){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createNewProduct(newProduct));
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok().build();
    }

}
