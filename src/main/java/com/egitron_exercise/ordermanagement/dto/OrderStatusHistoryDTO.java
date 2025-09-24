package com.egitron_exercise.ordermanagement.dto;

import java.time.LocalDateTime;

public class OrderStatusHistoryDTO {
    private String status;
    private LocalDateTime changedAt;

    public OrderStatusHistoryDTO(String status, LocalDateTime changedAt) {
        this.status = status;
        this.changedAt = changedAt;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}
