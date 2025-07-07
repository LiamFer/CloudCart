package com.liamfer.CloudCart.service;

import com.liamfer.CloudCart.dto.order.OrderDTO;
import com.liamfer.CloudCart.dto.stripe.StripeResponse;
import com.liamfer.CloudCart.entity.*;
import com.liamfer.CloudCart.exceptions.EmptyCartException;
import com.liamfer.CloudCart.exceptions.StockNotEnoughException;
import com.liamfer.CloudCart.repository.CartRepository;
import com.liamfer.CloudCart.repository.OrderRepository;
import com.liamfer.CloudCart.repository.ProductRepository;
import com.liamfer.CloudCart.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StripeService stripeService;
    private final ModelMapper modelMapper;

    public CheckoutService(UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository, OrderRepository orderRepository, StripeService stripeService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.stripeService = stripeService;
        this.modelMapper = modelMapper;
    }

    public StripeResponse createCheckoutOrder(UserDetails userDetails){
        UserEntity user = this.findUser(userDetails);
        CartEntity cart = this.checkCartExistence(user);

        if(cart.getItems().isEmpty()) throw new EmptyCartException("O Carrinho está vazio");
        this.checkItemsStock(cart.getItems());

        OrderEntity order = new OrderEntity();
        order.setUser(user);
        List<OrderItemEntity> orderItems = this.updateItemsStock(order,cart.getItems());
        order.setItems(orderItems);
        order.setTotal(orderItems.stream().map(item -> item.getQuantity() * item.getPrice())
                .reduce(0.0, Double::sum));

        cartRepository.deleteById(cart.getId());
        OrderEntity createdOrder = orderRepository.save(order);
        return stripeService.createPayment(createdOrder.getItems());
//        return modelMapper.map(orderRepository.save(order), OrderDTO.class);
    }

    public Page<OrderDTO> getCheckoutOrders(UserDetails userDetails, Pageable pageable){
        UserEntity user = this.findUser(userDetails);
        return orderRepository.findAllByUserId(user.getId(),pageable).map(order -> modelMapper.map(order, OrderDTO.class));
    }

    private void checkItemsStock(List<CartItemEntity> items){
        List<Long> ids = new ArrayList<>();
        for(CartItemEntity item : items){
            ProductEntity product = item.getProduct();
            if(product.getAvailable().equals(false) || product.getStock() < item.getQuantity()){
                ids.add(product.getId());
            }
        }
        if(!ids.isEmpty()) throw new StockNotEnoughException("Estoque insuficiente para alguns produtos",ids);
    }

    private List<OrderItemEntity> updateItemsStock(OrderEntity order,List<CartItemEntity> items){
        return items.stream().map(item -> {
            ProductEntity product = item.getProduct();
            int amount = product.getStock() - item.getQuantity();
            if(amount == 0) product.setAvailable(false);
            product.setStock(amount);
            return new OrderItemEntity(productRepository.save(product),order,item.getQuantity(),product.getPrice());
        }).toList();
    }

    private CartEntity checkCartExistence(UserEntity user){
        Optional<CartEntity> cart = cartRepository.findByUserId(user.getId());
        if(cart.isPresent()) return cart.get();
        return cartRepository.save(new CartEntity(user));
    }

    private UserEntity findUser(UserDetails user){
        Optional<UserEntity> userData = userRepository.findByEmail(user.getUsername());
        if(userData.isPresent()) return userData.get();
        throw new EntityNotFoundException("User Not Found");
    }
}
