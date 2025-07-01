package com.liamfer.CloudCart.service;

import com.liamfer.CloudCart.dto.cartItem.AddCartItemDTO;
import com.liamfer.CloudCart.dto.cartItem.CartItemAmountDTO;
import com.liamfer.CloudCart.dto.cartItem.CartItemResponseDTO;
import com.liamfer.CloudCart.entity.CartEntity;
import com.liamfer.CloudCart.entity.CartItemEntity;
import com.liamfer.CloudCart.entity.ProductEntity;
import com.liamfer.CloudCart.entity.UserEntity;
import com.liamfer.CloudCart.exceptions.ProductNotEnoughInStockException;
import com.liamfer.CloudCart.exceptions.ProductUnavailableException;
import com.liamfer.CloudCart.repository.CartItemRepository;
import com.liamfer.CloudCart.repository.CartRepository;
import com.liamfer.CloudCart.repository.ProductRepository;
import com.liamfer.CloudCart.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    public CartService(UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.modelMapper = modelMapper;
    }

    public CartItemResponseDTO addItemInCart(UserDetails userDetails, AddCartItemDTO addCartItemDTO){
        UserEntity user = this.findUser(userDetails);
        CartEntity cart = this.checkCartExistence(user);
        int amount = addCartItemDTO.getAmount();

        // Verificar se o Produto está disponivel
        ProductEntity product = this.checkProductIsAvailable(addCartItemDTO.getProductID());
        if(product.getStock() < amount) throw new ProductNotEnoughInStockException("Estoque Insuficiente");
        Optional<CartItemEntity> alreadyInCartItem = cart.getItems()
                .stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId())).findFirst();

        // Se o produto já estava no Carrinho
        if(alreadyInCartItem.isPresent()){
            CartItemEntity item = alreadyInCartItem.get();
            int newAmount = amount + item.getQuantity();
            if(product.getStock() < newAmount) throw new ProductNotEnoughInStockException("Estoque Insuficiente");
            item.setQuantity(newAmount);
            return modelMapper.map(cartItemRepository.save(item),CartItemResponseDTO.class);
        }
        CartItemEntity item = new CartItemEntity(cart,product,amount);
        return modelMapper.map(cartItemRepository.save(item),CartItemResponseDTO.class);
    }

    public CartItemResponseDTO editCartItemAmount(UserDetails userDetails,Long cartItemID,CartItemAmountDTO cartItemAmountDTO){
        UserEntity user = this.findUser(userDetails);
        CartEntity cart = this.checkCartExistence(user);
        int amount = cartItemAmountDTO.getAmount();

        Optional<CartItemEntity> cartItem = cartItemRepository.findByIdAndCartId(cartItemID,cart.getId());
        if(cartItem.isEmpty()) throw new EntityNotFoundException("Item não encontrado no Carrinho");
        CartItemEntity item = cartItem.get();
        ProductEntity product = item.getProduct();

        if(product.getStock() < amount) throw new ProductNotEnoughInStockException("Estoque Insuficiente");
        item.setQuantity(amount);
        return modelMapper.map(cartItemRepository.save(item),CartItemResponseDTO.class);
    }

    @Transactional
    public void deleteCartItem(UserDetails userDetails, Long id){
        UserEntity user = this.findUser(userDetails);
        CartEntity cart = this.checkCartExistence(user);
        Optional<CartItemEntity> cartItem = cartItemRepository.findByIdAndCartId(id,cart.getId());
        if(cartItem.isEmpty()) throw new EntityNotFoundException("Item não encontrado no Carrinho");
        cartItemRepository.deleteById(id);
    }

    // Busca o Carrinho, caso não exista cria um e retorna
    private CartEntity checkCartExistence(UserEntity user){
        Optional<CartEntity> cart = cartRepository.findByUserId(user.getId());
        if(cart.isPresent()) return cart.get();
        return cartRepository.save(new CartEntity(user));
    }

    private ProductEntity checkProductIsAvailable(Long id){
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        if (!Boolean.TRUE.equals(product.getAvailable()) || product.getStock() <= 0) {
            throw new ProductUnavailableException("Produto indisponível ou sem estoque");
        }
        return product;
    }

    private UserEntity findUser(UserDetails user){
        Optional<UserEntity> userData = userRepository.findByEmail(user.getUsername());
        if(userData.isPresent()) return userData.get();
        throw new EntityNotFoundException("User Not Found");
    }
}
