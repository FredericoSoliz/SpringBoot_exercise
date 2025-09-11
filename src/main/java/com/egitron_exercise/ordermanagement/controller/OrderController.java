package com.egitron_exercise.ordermanagement.controller;

import com.egitron_exercise.ordermanagement.dto.OrderRequestDTO;
import com.egitron_exercise.ordermanagement.dto.OrderResponseDTO;
import com.egitron_exercise.ordermanagement.external.validation.ValidationRequestDTO;
import com.egitron_exercise.ordermanagement.external.validation.ValidationResponseDTO;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.model.Order;
import com.egitron_exercise.ordermanagement.model.OrderStatus;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import com.egitron_exercise.ordermanagement.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final RestTemplate restTemplate;

    public OrderController(OrderRepository orderRepository, ClientRepository clientRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.restTemplate = restTemplate;
    }

    // GET /orders -> returns list of DTOs
    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponseDTO(
                        order.getOrderId(),
                        order.getStatus().name(),
                        order.getValue(),
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

        // Chamada à API externa
        ValidationRequestDTO validationRequest = new ValidationRequestDTO();
        validationRequest.setClientId(client.getClientId());
        validationRequest.setName(client.getName());
        validationRequest.setEmail(client.getEmail());

        ValidationResponseDTO validationResponse = restTemplate.postForObject(
                "http://localhost:8080/external/validate",
                validationRequest,
                ValidationResponseDTO.class
        );

        if (validationResponse == null || !"VALID".equals(validationResponse.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    validationResponse != null ? validationResponse.getReason() : "Validation failed"
            );
        }

        // only create order if validation is ok
        Order order = new Order();
        order.setClient(client);
        order.setStatus(OrderStatus.valueOf(orderRequest.getStatus().toUpperCase()));
        order.setValue(orderRequest.getValue());
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDTO(
                savedOrder.getOrderId(),
                savedOrder.getStatus().name(),
                savedOrder.getValue(),
                savedOrder.getCreatedAt(),
                savedOrder.getClient().getName(),
                savedOrder.getClient().getEmail()
        );
    }
}

