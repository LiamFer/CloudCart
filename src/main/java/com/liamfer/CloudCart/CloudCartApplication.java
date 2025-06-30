package com.liamfer.CloudCart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CloudCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudCartApplication.class, args);
	}

}
