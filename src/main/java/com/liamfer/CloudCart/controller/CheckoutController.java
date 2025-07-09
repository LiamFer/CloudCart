package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.APIMessage;
import com.liamfer.CloudCart.dto.order.OrderDTO;
import com.liamfer.CloudCart.dto.stripe.StripeResponse;
import com.liamfer.CloudCart.service.CheckoutService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<StripeResponse> generateCheckoutOrder(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.createCheckoutOrder(userDetails));
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<APIMessage<String>> cancelCheckoutOrder(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @PathVariable("id") Long paymentId){
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.createCheckoutOrder(userDetails));
    }

}
