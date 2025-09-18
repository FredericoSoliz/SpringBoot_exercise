package com.egitron_exercise.ordermanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponseDTO {
    private Long orderId;
    private String status;
    private BigDecimal value;
    private LocalDateTime createdAt;
    private String clientName;
    private String clientEmail;
    private boolean validation;

    public OrderResponseDTO(Long orderId, String status, BigDecimal value,
                            LocalDateTime createdAt, String clientName, String clientEmail, boolean validation) {
        this.orderId = orderId;
        this.status = status;
        this.value = value;
        this.createdAt = createdAt;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.validation = validation;
    }

    public Long getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public BigDecimal getValue() { return value; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getClientName() { return clientName; }
    public String getClientEmail() { return clientEmail; }
    public boolean isValidation() { return validation; }
}
