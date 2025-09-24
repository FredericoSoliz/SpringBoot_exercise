package com.egitron_exercise.ordermanagement.dto;

import com.egitron_exercise.ordermanagement.model.OrderStatus;

import java.math.BigDecimal;

public class OrderRequestDTO {
    private String status;
    private BigDecimal value;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
}

