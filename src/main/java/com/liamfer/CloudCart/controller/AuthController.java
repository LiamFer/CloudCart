package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.APIMessage;
import com.liamfer.CloudCart.dto.user.CreateUserDTO;
import com.liamfer.CloudCart.dto.user.GeneratedTokenResponseDTO;
import com.liamfer.CloudCart.dto.user.LoginUserDTO;
import com.liamfer.CloudCart.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Cria um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Email/CPF já estão em uso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/register")
    public ResponseEntity<APIMessage<String>> registerUser(@RequestBody @Valid CreateUserDTO createUserDTO){
        authService.registerUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIMessage<String>(HttpStatus.CREATED.value(), "Usuário criado!"));
    }

    @Operation(summary = "Faz Login com as Credenciais de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = APIMessage.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<GeneratedTokenResponseDTO> registerUser(@RequestBody @Valid LoginUserDTO LoginUserDTO){
        String token = authService.loginUser(LoginUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new GeneratedTokenResponseDTO(token));
    }
}
