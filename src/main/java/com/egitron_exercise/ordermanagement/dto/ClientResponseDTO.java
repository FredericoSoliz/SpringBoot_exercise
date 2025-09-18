package com.egitron_exercise.ordermanagement.dto;

import java.time.LocalDateTime;

public class ClientResponseDTO {
    private Long clientId;
    private String name;
    private String email;
    private LocalDateTime createdAt;

    public ClientResponseDTO(Long clientId, String name, String email, LocalDateTime createdAt) {
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Long getClientId() { return clientId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
