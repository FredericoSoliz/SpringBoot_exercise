package com.egitron_exercise.ordermanagement.external.validation;

public class ValidationResponseDTO {
    private String status;
    private String reason;

    public ValidationResponseDTO(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    // getters
    public String getStatus() { return status; }
    public String getReason() { return reason; }
}
