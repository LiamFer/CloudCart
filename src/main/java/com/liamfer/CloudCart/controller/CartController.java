package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.APIMessage;
import com.liamfer.CloudCart.dto.cart.AddCartItemDTO;
import com.liamfer.CloudCart.dto.cart.CartDTO;
import com.liamfer.CloudCart.dto.cart.CartItemAmountDTO;
import com.liamfer.CloudCart.dto.cart.CartItemResponseDTO;
import com.liamfer.CloudCart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Retorna os Produtos no Carrinho do Usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")})
    @GetMapping
    public ResponseEntity<CartDTO> getCartItems(@AuthenticationPrincipal UserDetails user){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.getCart(user));
    }

    @Operation(summary = "Adiciona um Produto no Carrinho do Usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto adicionado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class))),
            @ApiResponse(responseCode = "400", description = "Produto indisponível ou sem estoque",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))
    })
    @PostMapping
    public ResponseEntity<CartItemResponseDTO> addItemToCart(@AuthenticationPrincipal UserDetails user,
                                                             @RequestBody @Valid AddCartItemDTO addCartItemDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addItemInCart(user,addCartItemDTO));
    }

    @Operation(summary = "Edita um Produto no Carrinho do Usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto editado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado no Carrinho",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class))),
            @ApiResponse(responseCode = "400", description = "Produto indisponível ou sem estoque",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))
    })
    @PatchMapping("/item/{id}")
    public ResponseEntity<CartItemResponseDTO> editCartItemAmount(@AuthenticationPrincipal UserDetails user,
                                                             @RequestBody @Valid CartItemAmountDTO cartItemAmountDTO,
                                                                  @PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(cartService.editCartItemAmount(user,id,cartItemAmountDTO));
    }

    @Operation(summary = "Remove um Produto do Carrinho do Usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido do Carrinho com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado no Carrinho",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))
    })
    @DeleteMapping("/item/{id}")
    public ResponseEntity<CartItemResponseDTO> deleteCartItem(@AuthenticationPrincipal UserDetails user,
                                               @PathVariable("id") Long id){
        cartService.deleteCartItem(user,id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
