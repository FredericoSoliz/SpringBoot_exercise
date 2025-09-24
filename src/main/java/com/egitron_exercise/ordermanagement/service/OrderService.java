package com.egitron_exercise.ordermanagement.service;

import com.egitron_exercise.ordermanagement.dto.OrderRequestDTO;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.model.Order;
import com.egitron_exercise.ordermanagement.model.OrderStatus;
import com.egitron_exercise.ordermanagement.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderRequestDTO orderRequest, Client client) {
        Order order = new Order();
        order.setClient(client);
        order.setStatus(OrderStatus.valueOf(orderRequest.getStatus().toUpperCase()));
        order.setValue(orderRequest.getValue());
        order.setCreatedAt(LocalDateTime.now());
        order.setValidation(true);

        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findByStatus(String status) {
        try {
            return orderRepository.findByStatus(OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status: " + status);
        }
    }

    public List<Order> findByDateRange(String startDate, String endDate) {
        return orderRepository.findByCreatedAtBetween(
                LocalDateTime.parse(startDate + "T00:00:00"),
                LocalDateTime.parse(endDate + "T23:59:59")
        );
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }
}
