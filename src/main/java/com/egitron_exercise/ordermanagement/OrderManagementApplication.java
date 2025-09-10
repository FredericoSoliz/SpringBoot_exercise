package com.egitron_exercise.ordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.egitron_exercise.ordermanagement.controller",
        "com.egitron_exercise.ordermanagement.repository",
        "com.egitron_exercise.ordermanagement.model"
})
public class OrderManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementApplication.class, args);
    }
}

