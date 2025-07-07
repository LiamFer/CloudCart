package com.liamfer.CloudCart.dto.stripe;

public record StripeResponse(String status,
                             String mesage,
                             String sessionId,
                             String sessionUrl) {
}
