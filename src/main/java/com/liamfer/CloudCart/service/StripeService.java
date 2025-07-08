package com.liamfer.CloudCart.service;

import com.liamfer.CloudCart.dto.stripe.StripeResponse;
import com.liamfer.CloudCart.entity.OrderEntity;
import com.liamfer.CloudCart.entity.OrderItemEntity;
import com.liamfer.CloudCart.entity.PaymentEntity;
import com.liamfer.CloudCart.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StripeService {
    private final PaymentRepository paymentRepository;
    @Value("${spring.stripe.key}")
    private String stripeKey;

    public StripeService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    public StripeResponse createPayment(OrderEntity order){
        Stripe.apiKey = stripeKey;
        List<SessionCreateParams.LineItem> lineItems = order.getItems().stream()
                .map(item -> SessionCreateParams.LineItem.builder()
                        .setQuantity((long) item.getQuantity())
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("brl")
                                        .setUnitAmount((long) (item.getPrice() * 100)) // em centavos
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName(item.getProduct().getName())
                                                        .build()
                                        )
                                        .build()
                        )
                        .build()
                )
                .toList();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/sucess")
                .setCancelUrl("http://localhost:3000/canceled")
                .addAllLineItem(lineItems)
                .build();

        try{
            Session session = Session.create(params);
            PaymentIntent paymentIntent = PaymentIntent.retrieve(session.getPaymentIntent());
            String paymentMethod = PaymentMethod.retrieve(paymentIntent.getPaymentMethod()).getType();
            PaymentEntity payment = new PaymentEntity(session.getId(), session.getStatus(), paymentMethod, order.getTotal(), order);
            paymentRepository.save(payment);
            return new StripeResponse(session.getStatus(),"Payment Created", session.getId(), session.getUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
