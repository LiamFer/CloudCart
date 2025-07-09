package com.liamfer.CloudCart.service;

import com.liamfer.CloudCart.dto.stripe.StripeResponse;
import com.liamfer.CloudCart.entity.OrderEntity;
import com.liamfer.CloudCart.entity.OrderItemEntity;
import com.liamfer.CloudCart.entity.PaymentEntity;
import com.liamfer.CloudCart.entity.ProductEntity;
import com.liamfer.CloudCart.repository.PaymentRepository;
import com.liamfer.CloudCart.repository.ProductRepository;
import com.stripe.Stripe;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            PaymentEntity payment = new PaymentEntity(session.getId(), session.getStatus(), order.getTotal(), order);
            paymentRepository.save(payment);
            return new StripeResponse(session.getStatus(),"Payment Created", session.getId(), session.getUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePayment(Session session){
        String paymentId = session.getPaymentIntent();
        PaymentEntity payment = this.findPayment(session.getId());
        try{
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentId);
            String status = paymentIntent.getStatus();

            // Pegando o método de pagamento utilizado
            String paymentMethodId = paymentIntent.getPaymentMethod();
            PaymentMethod paymentMethodObj = PaymentMethod.retrieve(paymentMethodId);
            String paymentMethodType = paymentMethodObj.getType();

            payment.setStatus(status);
            payment.setPaymentMethod(paymentMethodType);
            paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PaymentEntity cancelPayment(Long paymentId) {
        PaymentEntity payment = this.findPaymentById(paymentId);
        try {
            Session session = Session.retrieve(payment.getStripePaymentId());
            String paymentIntentId = session.getPaymentIntent();

            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            PaymentIntent canceledIntent = paymentIntent.cancel();

            payment.setStatus(canceledIntent.getStatus());
            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cancelar o pagamento", e);
        }
    }

    private PaymentEntity findPaymentById(Long paymentId){
        Optional<PaymentEntity> payment = paymentRepository.findById(paymentId);
        if(payment.isPresent()) return payment.get();
        throw new EntityNotFoundException("Resource not Found");
    }

    private PaymentEntity findPayment(String paymentId){
        Optional<PaymentEntity> payment = paymentRepository.findBystripePaymentId(paymentId);
        if(payment.isPresent()) return payment.get();
        throw new EntityNotFoundException("Resource not Found");
    }
}
