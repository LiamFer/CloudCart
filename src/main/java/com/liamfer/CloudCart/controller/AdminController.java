package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.APIMessage;
import com.liamfer.CloudCart.dto.image.ImageResponseDTO;
import com.liamfer.CloudCart.dto.product.ProductDTO;
import com.liamfer.CloudCart.dto.product.ProductResponseDTO;
import com.liamfer.CloudCart.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {
    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Adiciona um novo Produto no Site")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))
    })
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> createNewProduct(@RequestBody @Valid ProductDTO newProduct) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createNewProduct(newProduct));
    }

    @Operation(summary = "Edita os dados de um Produto do Site")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto editado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))
    })
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id,
                                                            @RequestBody @Valid ProductDTO product) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, product));
    }

    @Operation(summary = "Deleta um Produto do Site")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class))),
            @ApiResponse(responseCode = "400", description = "Produto pertence a um pedido e não pode ser deletado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))
    })
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Faz Upload das Imagens de um Produto no Cloudinary")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Imagens salvas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao fazer upload para o Cloudinary",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))
    })
    @PostMapping(value = "/products/{id}/images", consumes = "multipart/form-data")
    public ResponseEntity<List<ImageResponseDTO>> uploadImages(
            @PathVariable("id") Long productID,
            @Parameter(description = "Imagens do produto", required = true,
                    content = @Content(mediaType = "application/octet-stream",
                            schema = @Schema(type = "string", format = "binary")))
            @RequestPart("image") List<MultipartFile> images) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.uploadProductImages(productID, images));
    }


    @DeleteMapping("/products/images/{id}")
    public ResponseEntity<List<ImageResponseDTO>> deleteImage(@PathVariable("id") Long imageID) {
        productService.deleteProductImage(imageID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
