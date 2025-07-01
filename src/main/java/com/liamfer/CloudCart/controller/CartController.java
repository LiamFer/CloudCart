package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.cartItem.AddCartItemDTO;
import com.liamfer.CloudCart.dto.cartItem.CartItemResponseDTO;
import com.liamfer.CloudCart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartItemResponseDTO> addItemToCart(@AuthenticationPrincipal UserDetails user,
                                                             @RequestBody @Valid AddCartItemDTO addCartItemDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addItemInCart(user,addCartItemDTO));
    }
}
