package com.liamfer.CloudCart.controller;

import com.liamfer.CloudCart.dto.APIMessage;
import com.liamfer.CloudCart.dto.CreateUserDTO;
import com.liamfer.CloudCart.dto.GeneratedTokenResponseDTO;
import com.liamfer.CloudCart.dto.LoginUserDTO;
import com.liamfer.CloudCart.service.AuthService;
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

    @PostMapping("/register")
    public ResponseEntity<APIMessage<String>> registerUser(@RequestBody @Valid CreateUserDTO createUserDTO){
        authService.registerUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new APIMessage<String>(HttpStatus.CREATED.value(), "Usuário criado!"));
    }

    @PostMapping("/login")
    public ResponseEntity<GeneratedTokenResponseDTO> registerUser(@RequestBody @Valid LoginUserDTO LoginUserDTO){
        String token = authService.loginUser(LoginUserDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new GeneratedTokenResponseDTO(token));
    }
}
