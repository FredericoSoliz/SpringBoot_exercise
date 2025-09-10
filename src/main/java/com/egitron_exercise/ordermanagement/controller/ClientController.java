package com.egitron_exercise.ordermanagement.controller;

import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // GET /clients -> lista todos os clientes
    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // POST /clients -> cria um novo cliente
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }
}
