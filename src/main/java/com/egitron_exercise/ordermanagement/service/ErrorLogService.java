package com.egitron_exercise.ordermanagement.service;

import com.egitron_exercise.ordermanagement.model.ErrorLog;
import com.egitron_exercise.ordermanagement.repository.ErrorLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ErrorLogService {

    private final ErrorLogRepository errorLogRepository;

    public ErrorLogService(ErrorLogRepository errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }

    public void logError(String message) {
        ErrorLog log = new ErrorLog();
        log.setMessage(message);
        log.setTimestamp(LocalDateTime.now());
        errorLogRepository.save(log);
    }

    public List<ErrorLog> findAll() {
        return errorLogRepository.findAll();
    }

    public List<ErrorLog> findSince(LocalDateTime since) {
        return errorLogRepository.findByTimestampAfter(since);
    }
}
