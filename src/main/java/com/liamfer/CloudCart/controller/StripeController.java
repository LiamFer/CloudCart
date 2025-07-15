package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.APIMessage;
import com.liamfer.CloudCart.service.StripeService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Scanner;

@RestController
@RequestMapping("/stripe")
public class StripeController {
    private final StripeService stripeService;
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @Operation(summary = "Webhook para Transações do Stripe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recebido"),
            @ApiResponse(responseCode = "400", description = "Requisição Inválida do Stripe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))})
    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) {
        String payload;
        String sigHeader = request.getHeader("Stripe-Signature");

        try (Scanner scanner = new Scanner(request.getInputStream(), "UTF-8")) {
            payload = scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Stripe Request");
        }

        Event event;
        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, endpointSecret
            );
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Signature");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject()
                    .orElseThrow(() -> new RuntimeException("Erro ao desserializar o webhook"));
            stripeService.updatePayment(session);
        }

        return ResponseEntity.ok("Recebido");
    }
}
