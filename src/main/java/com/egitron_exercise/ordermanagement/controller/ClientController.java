package com.egitron_exercise.ordermanagement.controller;

import com.egitron_exercise.ordermanagement.dto.ClientRequestDTO;
import com.egitron_exercise.ordermanagement.dto.ClientResponseDTO;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // GET /clients -> list all in DTO format
    @GetMapping
    public List<ClientResponseDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(client -> new ClientResponseDTO(
                        client.getClientId(),
                        client.getName(),
                        client.getEmail(),
                        client.getCreatedAt()
                ))
                .toList();
    }

    // POST /clients -> creates new client using DTO
    @PostMapping
    public ClientResponseDTO createClient(@RequestBody ClientRequestDTO clientRequest) {
        Client client = new Client();
        client.setName(clientRequest.getName());
        client.setEmail(clientRequest.getEmail());
        client.setCreatedAt(LocalDateTime.now());

        Client saved = clientRepository.save(client);

        return new ClientResponseDTO(
                saved.getClientId(),
                saved.getName(),
                saved.getEmail(),
                saved.getCreatedAt()
        );
    }
}
