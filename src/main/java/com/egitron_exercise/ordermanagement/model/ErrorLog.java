package com.egitron_exercise.ordermanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorId;

    private LocalDateTime timestamp;
    private String message;

    @Column(length = 2000) // stacktrace size
    private String stacktrace;

    public Long getErrorId() { return errorId; }
    public void setErrorId(Long errorId) { this.errorId = errorId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStacktrace() { return stacktrace; }
    public void setStacktrace(String stacktrace) { this.stacktrace = stacktrace; }
}
