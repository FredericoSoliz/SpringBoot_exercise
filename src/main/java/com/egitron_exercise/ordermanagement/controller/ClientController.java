package com.egitron_exercise.ordermanagement.controller;

import com.egitron_exercise.ordermanagement.dto.ClientResponseDTO;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // GET /clients -> list all in DTO format
    @GetMapping
    public List<ClientResponseDTO> getAllClients() {
        return clientService.findAll().stream()
                .map(client -> new ClientResponseDTO(
                        client.getClientId(),
                        client.getName(),
                        client.getEmail(),
                        client.getCreatedAt()
                ))
                .toList();
    }
}

