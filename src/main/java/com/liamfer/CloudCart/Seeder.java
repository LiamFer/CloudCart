package com.liamfer.CloudCart;

import com.liamfer.CloudCart.entity.ProductEntity;
import com.liamfer.CloudCart.entity.UserEntity;
import com.liamfer.CloudCart.enums.UserRole;
import com.liamfer.CloudCart.repository.ProductRepository;
import com.liamfer.CloudCart.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Seeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public Seeder(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        boolean seedRequested = Arrays.asList(args).contains("--seed");
        if (!seedRequested) return;

        if (userRepository.count() == 0) {
            UserEntity admin = new UserEntity();
            admin.setEmail("admin@email.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
        }


        System.out.println("✅ Seed executado com sucesso!");
    }
}
