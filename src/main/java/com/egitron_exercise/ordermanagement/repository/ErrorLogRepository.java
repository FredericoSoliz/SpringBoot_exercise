package com.egitron_exercise.ordermanagement.repository;

import com.egitron_exercise.ordermanagement.model.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
}
