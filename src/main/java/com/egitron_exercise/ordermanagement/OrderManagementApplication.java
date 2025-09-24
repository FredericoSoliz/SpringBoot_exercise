package com.egitron_exercise.ordermanagement;

import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.model.Role;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@SpringBootApplication(scanBasePackages = {
        "com.egitron_exercise.ordermanagement.controller",
        "com.egitron_exercise.ordermanagement.repository",
        "com.egitron_exercise.ordermanagement.model",
        "com.egitron_exercise.ordermanagement.external",
        "com.egitron_exercise.ordermanagement.config",
        "com.egitron_exercise.ordermanagement.dto",
        "com.egitron_exercise.ordermanagement.jwt",
        "com.egitron_exercise.ordermanagement.service"
})
public class OrderManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner initAdmin(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (clientRepository.findByEmail("admin@mail.com").isEmpty()) {
                Client admin = new Client();
                admin.setName("admin");
                admin.setEmail("admin@mail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                admin.setCreatedAt(LocalDateTime.now());

                clientRepository.save(admin);
                System.out.println(">>> Default ADMIN user created: admin@mail.com / admin123");
            }
        };
    }
}

