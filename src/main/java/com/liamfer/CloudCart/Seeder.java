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
import java.util.List;

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
            admin.setName("John Doe");
            admin.setEmail("admin@email.com");
            admin.setCpf("00000000000");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
            System.out.println("✅ Admin User criado com sucesso!");

            UserEntity user = new UserEntity();
            user.setName("Jane Smith");
            user.setEmail("user@email.com");
            user.setCpf("11111111111");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(UserRole.STANDARD);
            userRepository.save(user);
            System.out.println("✅ Standard User criado com sucesso!");
        }

        if (productRepository.count() == 0) {
            List<ProductEntity> products = List.of(
                    new ProductEntity("Mouse Logitech MX Master 3", "Mouse sem fio de alta precisão com rolagem MagSpeed", 399.90, 25, true),
                    new ProductEntity("Monitor LG UltraGear 27\"", "Monitor gamer 144Hz 1ms IPS Full HD", 1299.00, 10, true),
                    new ProductEntity("Notebook Dell Inspiron 15", "Intel i5, 8GB RAM, SSD 256GB, Windows 11", 3499.00, 5, true),
                    new ProductEntity("Teclado Mecânico Redragon Kumara", "Switch Outemu Blue, iluminação vermelha", 199.99, 30, true),
                    new ProductEntity("Smartphone Samsung Galaxy S23", "128GB, Câmera tripla, Tela AMOLED 6.1\"", 4499.00, 8, true),
                    new ProductEntity("Headset HyperX Cloud II", "Som 7.1 Virtual Surround, drivers de 53mm", 499.00, 12, true),
                    new ProductEntity("Cadeira Gamer ThunderX3", "Reclinável, almofadas lombar e pescoço inclusas", 999.00, 7, true),
                    new ProductEntity("Kindle Paperwhite 11ª Geração", "Tela de 6.8\", à prova d’água, luz ajustável", 649.90, 20, true),
                    new ProductEntity("Echo Dot 5ª geração", "Smart speaker com Alexa, som melhorado", 379.00, 15, true),
                    new ProductEntity("Placa de Vídeo RTX 4060 Ti", "8GB GDDR6, DLSS 3, Ray Tracing", 2699.00, 4, true)
            );
            productRepository.saveAll(products);
            System.out.println("✅ Produtos Criados com sucesso!");
        }


        System.out.println("✅ Seed executado com sucesso!");
    }
}
