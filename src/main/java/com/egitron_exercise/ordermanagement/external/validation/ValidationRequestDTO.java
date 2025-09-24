package com.egitron_exercise.ordermanagement.external.validation;

public class ValidationRequestDTO {
    private Long clientId;   // can be null if it's a new client
    private String name;
    private String email;
    private String status;

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
