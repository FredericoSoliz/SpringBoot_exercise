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
import com.egitron_exercise.ordermanagement.service.ExternalValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ExternalValidationService  externalValidationService;

    public OrderController(OrderRepository orderRepository,
                           ClientRepository clientRepository,
                           ExternalValidationService externalValidationService) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.externalValidationService = externalValidationService;
    }

    // POST /orders -> receive and returns DTO
    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequest, Principal principal) {

        Client client = clientRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Client not found"));

        // mock call to external validation
        ValidationRequestDTO validationRequest = new ValidationRequestDTO();
        validationRequest.setClientId(client.getClientId());
        validationRequest.setName(client.getName());
        validationRequest.setEmail(client.getEmail());

        ValidationResponseDTO validationResponse = externalValidationService.validateClient(
                validationRequest,
                orderRequest.getValue().doubleValue()
        );

        // doesn't create order if invalid
        if (validationResponse == null || !"VALID".equals(validationResponse.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    validationResponse != null ? validationResponse.getReason() : "Error in external validation"
            );
        }

        // create order
        Order order = new Order();
        order.setClient(client);
        order.setStatus(OrderStatus.valueOf(orderRequest.getStatus().toUpperCase()));
        order.setValue(orderRequest.getValue());
        order.setCreatedAt(LocalDateTime.now());
        order.setValidation(true);

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDTO(
                savedOrder.getOrderId(),
                savedOrder.getStatus().name(),
                savedOrder.getValue(),
                savedOrder.getCreatedAt(),
                savedOrder.getClient().getName(),
                savedOrder.getClient().getEmail(),
                savedOrder.isValidation()
        );
    }

    // GET /orders?status=...&startDate=...&endDate=...
    @GetMapping
    public List<OrderResponseDTO> getOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        List<Order> orders;

        if (status != null) {
            orders = orderRepository.findByStatus(OrderStatus.valueOf(status.toUpperCase()));
        } else if (startDate != null && endDate != null) {
            orders = orderRepository.findByCreatedAtBetween(
                    LocalDateTime.parse(startDate + "T00:00:00"),
                    LocalDateTime.parse(endDate + "T23:59:59")
            );
        } else {
            orders = orderRepository.findAll();
        }

        return orders.stream()
                .map(order -> new OrderResponseDTO(
                        order.getOrderId(),
                        order.getStatus().name(),
                        order.getValue(),
                        order.getCreatedAt(),
                        order.getClient().getName(),
                        order.getClient().getEmail(),
                        order.isValidation()
                ))
                .toList();
    }

    // GET /orders/{id}
    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        return new OrderResponseDTO(
                order.getOrderId(),
                order.getStatus().name(),
                order.getValue(),
                order.getCreatedAt(),
                order.getClient().getName(),
                order.getClient().getEmail(),
                order.isValidation()
        );
    }





}

