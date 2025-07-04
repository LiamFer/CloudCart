package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.order.OrderDTO;
import com.liamfer.CloudCart.service.CheckoutService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getUserCheckoutOrders(@AuthenticationPrincipal UserDetails userDetails,
                                                                Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(checkoutService.getCheckoutOrders(userDetails,pageable));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> generateCheckoutOrder(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.createCheckoutOrder(userDetails));
    }

}
