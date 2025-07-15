package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.APIMessage;
import com.liamfer.CloudCart.dto.product.ProductSimpleDTO;
import com.liamfer.CloudCart.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Retorna informações dos Produtos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")})
    @GetMapping
    public ResponseEntity<Page<ProductSimpleDTO>> getProducts(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProducts(pageable));
    }

    @Operation(summary = "Retorna informações de um Produto Específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<ProductSimpleDTO> getProduct(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductById(id));
    }
}
