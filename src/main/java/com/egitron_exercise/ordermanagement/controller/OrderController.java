package com.egitron_exercise.ordermanagement.controller;

import com.egitron_exercise.ordermanagement.dto.OrderRequestDTO;
import com.egitron_exercise.ordermanagement.dto.OrderResponseDTO;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.model.Order;
import com.egitron_exercise.ordermanagement.model.OrderStatus;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import com.egitron_exercise.ordermanagement.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    public OrderController(OrderRepository orderRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    // GET /orders -> returns list of DTOs
    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponseDTO(
                        order.getOrderId(),
                        order.getStatus().name(),
                        order.getValue(),
                        order.getValidation(),
                        order.getCreatedAt(),
                        order.getClient().getName(),
                        order.getClient().getEmail()
                ))
                .toList();
    }

    // POST /orders -> receive and returns DTO
    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequest) {
        Client client = clientRepository.findById(orderRequest.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with id " + orderRequest.getClientId()));

        Order order = new Order();
        order.setClient(client);
        order.setStatus(OrderStatus.valueOf(orderRequest.getStatus().toUpperCase())); // converts string -> enum
        order.setValue(orderRequest.getValue());
        order.setValidation(orderRequest.getValidation());
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDTO(
                savedOrder.getOrderId(),
                savedOrder.getStatus().name(),
                savedOrder.getValue(),
                savedOrder.getValidation(),
                savedOrder.getCreatedAt(),
                savedOrder.getClient().getName(),
                savedOrder.getClient().getEmail()
        );
    }
}
