package com.egitron_exercise.ordermanagement.external.validation;

import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external")
public class ExternalValidationController {

    private final ClientRepository clientRepository;

    public ExternalValidationController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping("/validate")
    public ValidationResponseDTO validateClient(@RequestBody ValidationRequestDTO request) {
        // Verifica duplicado de nome
        Client existingByName = clientRepository.findByName(request.getName());
        if (existingByName != null && !existingByName.getClientId().equals(request.getClientId())) {
            return new ValidationResponseDTO("INVALID", "Name already in use");
        }

        // Verifica duplicado de email
        Client existingByEmail = clientRepository.findByEmail(request.getEmail());
        if (existingByEmail != null && !existingByEmail.getClientId().equals(request.getClientId())) {
            return new ValidationResponseDTO("INVALID", "Email already in use");
        }

        return new ValidationResponseDTO("VALID", "Client is valid");
    }
}

