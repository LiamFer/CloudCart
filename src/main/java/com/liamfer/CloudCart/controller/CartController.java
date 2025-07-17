package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.cart.AddCartItemDTO;
import com.liamfer.CloudCart.dto.cart.CartDTO;
import com.liamfer.CloudCart.dto.cart.CartItemAmountDTO;
import com.liamfer.CloudCart.dto.cart.CartItemResponseDTO;
import com.liamfer.CloudCart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCartItems(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCart(user));
    }

    @PostMapping
    public ResponseEntity<CartItemResponseDTO> addItemToCart(@AuthenticationPrincipal UserDetails user,
                                                             @RequestBody @Valid AddCartItemDTO addCartItemDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addItemInCart(user,addCartItemDTO));
    }

    @PatchMapping("/item/{id}")
    public ResponseEntity<CartItemResponseDTO> editCartItemAmount(@AuthenticationPrincipal UserDetails user,
                                                             @RequestBody @Valid CartItemAmountDTO cartItemAmountDTO,
                                                                  @PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.editCartItemAmount(user,id,cartItemAmountDTO));
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<CartItemResponseDTO> deleteCartItem(@AuthenticationPrincipal UserDetails user,
                                               @PathVariable("id") Long id){
        cartService.deleteCartItem(user,id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
