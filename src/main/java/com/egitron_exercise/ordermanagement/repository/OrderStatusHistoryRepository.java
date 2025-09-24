package com.egitron_exercise.ordermanagement.repository;

import com.egitron_exercise.ordermanagement.model.Order;
import com.egitron_exercise.ordermanagement.model.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    List<OrderStatusHistory> findByOrder(Order order);
}
