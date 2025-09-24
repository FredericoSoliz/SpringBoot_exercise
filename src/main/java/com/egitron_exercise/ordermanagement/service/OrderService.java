package com.egitron_exercise.ordermanagement.service;

import com.egitron_exercise.ordermanagement.dto.OrderRequestDTO;
import com.egitron_exercise.ordermanagement.dto.OrderStatusHistoryDTO;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.model.Order;
import com.egitron_exercise.ordermanagement.model.OrderStatus;
import com.egitron_exercise.ordermanagement.model.OrderStatusHistory;
import com.egitron_exercise.ordermanagement.repository.OrderRepository;
import com.egitron_exercise.ordermanagement.repository.OrderStatusHistoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    public OrderService(OrderRepository orderRepository, OrderStatusHistoryRepository orderStatusHistoryRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
    }

    public Order createOrder(OrderRequestDTO orderRequest, Client client) {

        Order order = new Order();
        order.setClient(client);
        order.setStatus(OrderStatus.PENDENTE);
        order.setValue(orderRequest.getValue());
        order.setCreatedAt(LocalDateTime.now());
        order.setValidation(true);

        Order savedOrder = orderRepository.save(order);

        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(savedOrder);
        history.setStatus(OrderStatus.PENDENTE);
        history.setChangedAt(LocalDateTime.now());

        orderStatusHistoryRepository.save(history);

        return savedOrder;
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        OrderStatus status;
        try {
            status = OrderStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid status. Use only APROVADO, PENDENTE or REJEITADO."
            );
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);

        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(updatedOrder);
        history.setStatus(status);
        history.setChangedAt(LocalDateTime.now());

        orderStatusHistoryRepository.save(history);

        return updatedOrder;
    }

    public List<OrderStatusHistoryDTO> getOrderHistory(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        List<OrderStatusHistory> historyList = orderStatusHistoryRepository.findByOrder(order);

        return historyList.stream()
                .map(h -> new OrderStatusHistoryDTO(h.getStatus().name(), h.getChangedAt()))
                .toList();
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
