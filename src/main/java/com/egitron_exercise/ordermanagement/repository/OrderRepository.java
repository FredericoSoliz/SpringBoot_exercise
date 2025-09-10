package com.egitron_exercise.ordermanagement.repository;

import com.egitron_exercise.ordermanagement.model.Order;
import com.egitron_exercise.ordermanagement.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
}
