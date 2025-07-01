package com.liamfer.CloudCart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @PostMapping
    public ResponseEntity<?> addItemToCart(){
        return ResponseEntity.ok("");
    }
}
