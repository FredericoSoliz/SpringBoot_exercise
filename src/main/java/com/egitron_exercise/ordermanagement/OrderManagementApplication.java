package com.egitron_exercise.ordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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
}

