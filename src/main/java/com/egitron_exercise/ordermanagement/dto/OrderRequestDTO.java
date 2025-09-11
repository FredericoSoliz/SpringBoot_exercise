package com.egitron_exercise.ordermanagement.dto;

import java.math.BigDecimal;

public class OrderRequestDTO {
    private Long clientId;
    private BigDecimal value;
    private String status;

    // getters and setters
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
