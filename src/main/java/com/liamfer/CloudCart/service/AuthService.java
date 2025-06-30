package com.liamfer.CloudCart.service;

import com.liamfer.CloudCart.dto.APIMessage;
import com.liamfer.CloudCart.dto.CreateUserDTO;
import com.liamfer.CloudCart.entity.UserEntity;
import com.liamfer.CloudCart.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void registerUser(CreateUserDTO createUserDTO){
        this.checkAvailable(createUserDTO.email(), createUserDTO.cpf());
        String hashedPassword = passwordEncoder.encode(createUserDTO.password());

        UserEntity user = new UserEntity();
        user.setName(createUserDTO.name());
        user.setCpf(createUserDTO.cpf());
        user.setEmail(createUserDTO.email());
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    private UserDetails findUser(String email){
        Optional<UserDetails> user = userRepository.findByEmail(email);
        if(user.isPresent()) return user.get();
        throw new EntityNotFoundException("Resource not Found");
    }

    // Função pra verificar se Email/CPF estão disponiveis para uso
    private void checkAvailable(String email,String cpf){
        if(userRepository.findByEmail(email).isPresent()) throw new EntityExistsException("Email already in use!");
        if(userRepository.findByCpf(cpf).isPresent()) throw new EntityExistsException("CPF already in use!");
    }
}
