package com.liamfer.CloudCart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${spring.stripe.key}")
    private String stripeKey;


}
