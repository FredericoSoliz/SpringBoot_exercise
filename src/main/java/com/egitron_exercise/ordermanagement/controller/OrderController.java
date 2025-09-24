package com.egitron_exercise.ordermanagement.controller;

import com.egitron_exercise.ordermanagement.dto.OrderRequestDTO;
import com.egitron_exercise.ordermanagement.dto.OrderResponseDTO;
import com.egitron_exercise.ordermanagement.dto.OrderStatusHistoryDTO;
import com.egitron_exercise.ordermanagement.external.validation.ValidationRequestDTO;
import com.egitron_exercise.ordermanagement.external.validation.ValidationResponseDTO;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.model.Order;
import com.egitron_exercise.ordermanagement.service.ClientService;
import com.egitron_exercise.ordermanagement.service.ExternalValidationService;
import com.egitron_exercise.ordermanagement.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ClientService clientService;
    private final ExternalValidationService externalValidationService;

    public OrderController(OrderService orderService, ClientService clientService, ExternalValidationService externalValidationService) {
        this.orderService = orderService;
        this.clientService = clientService;
        this.externalValidationService = externalValidationService;
    }

    // POST /orders
    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequest, Principal principal) {
        Client client = clientService.findByEmail(principal.getName());

        ValidationRequestDTO validationRequest = new ValidationRequestDTO();
        validationRequest.setClientId(client.getClientId());
        validationRequest.setName(client.getName());
        validationRequest.setEmail(client.getEmail());
        validationRequest.setStatus(orderRequest.getStatus());

        ValidationResponseDTO validationResponse = externalValidationService.validateClient(
                validationRequest,
                orderRequest.getValue().doubleValue()
        );

        if (validationResponse == null || !"VALID".equals(validationResponse.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    validationResponse != null ? validationResponse.getReason() : "Error in external validation"
            );
        }

        Order savedOrder = orderService.createOrder(orderRequest, client);
        return mapToDTO(savedOrder);
    }

    // GET /orders?status=...&startDate=...&endDate=...
    @GetMapping
    public List<OrderResponseDTO> getOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        List<Order> orders;

        if (status != null) {
            orders = orderService.findByStatus(status);
        } else if (startDate != null && endDate != null) {
            orders = orderService.findByDateRange(startDate, endDate);
        } else {
            orders = orderService.findAll();
        }

        return orders.stream().map(this::mapToDTO).toList();
    }

    // GET /orders/{id}
    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {
        return mapToDTO(orderService.findById(id));
    }

    private OrderResponseDTO mapToDTO(Order order) {
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

    // PATCH /orders/{id}/status
    @PatchMapping("/{id}/status")
    public OrderResponseDTO updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String newStatus) {

        Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
        return mapToDTO(updatedOrder);
    }

    // GET /orders/{id}/history
    @GetMapping("/{id}/history")
    public List<OrderStatusHistoryDTO> getOrderHistory(@PathVariable Long id) {
        return orderService.getOrderHistory(id);
    }




}

