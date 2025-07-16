package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.APIMessage;
import com.liamfer.CloudCart.dto.order.OrderDTO;
import com.liamfer.CloudCart.dto.stripe.StripeResponse;
import com.liamfer.CloudCart.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Retorna informações das Ordens de Compra do Usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")})
    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getUserCheckoutOrders(@AuthenticationPrincipal UserDetails userDetails,
                                                                Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(checkoutService.getCheckoutOrders(userDetails,pageable));
    }

    @Operation(summary = "Gera uma Ordem de Compra usando o Carrinho do Usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ordem gerada com sucesso"),
            @ApiResponse(responseCode = "400", description = "O Carrinho está vazio",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class))),
            @ApiResponse(responseCode = "400", description = "Estoque insuficiente para alguns produtos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))
    })
    @PostMapping
    public ResponseEntity<StripeResponse> generateCheckoutOrder(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.createCheckoutOrder(userDetails));
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<APIMessage<String>> cancelCheckoutOrder(@AuthenticationPrincipal UserDetails userDetails,
                                                                  @PathVariable("id") Long paymentId){
        checkoutService.cancelCheckoutOrder(userDetails,paymentId);
        APIMessage<String> response = new APIMessage<>(HttpStatus.OK.value(), "Order Canceled Successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
