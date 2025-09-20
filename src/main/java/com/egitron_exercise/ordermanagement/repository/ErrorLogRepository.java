package com.egitron_exercise.ordermanagement.repository;

import com.egitron_exercise.ordermanagement.model.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
    List<ErrorLog> findByTimestampAfter(LocalDateTime timestamp);
}
