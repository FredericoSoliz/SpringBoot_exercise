package com.egitron_exercise.ordermanagement.repository;

import com.egitron_exercise.ordermanagement.model.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
}
