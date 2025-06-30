package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.product.ProductSimpleDTO;
import com.liamfer.CloudCart.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductSimpleDTO>> getProducts(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSimpleDTO> getProducts(@PathVariable("id") Long id){
        return ResponseEntity.ok().build();
    }
}
