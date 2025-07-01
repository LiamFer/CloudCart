package com.liamfer.CloudCart.service;

import com.liamfer.CloudCart.entity.CartEntity;
import com.liamfer.CloudCart.entity.UserEntity;
import com.liamfer.CloudCart.repository.CartItemRepository;
import com.liamfer.CloudCart.repository.CartRepository;
import com.liamfer.CloudCart.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void addItemInCart(UserDetails userDetails){
        UserEntity user = this.findUser(userDetails);
        CartEntity cart = this.checkCartExistence(user);

        // Verificar se o Produto já está no carrinho

    }

    // Busca o Carrinho, caso não exista cria um e retorna
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
