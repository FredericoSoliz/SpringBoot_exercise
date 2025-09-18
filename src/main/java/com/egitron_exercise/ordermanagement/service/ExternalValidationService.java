package com.egitron_exercise.ordermanagement.service;

import com.egitron_exercise.ordermanagement.external.validation.ValidationRequestDTO;
import com.egitron_exercise.ordermanagement.external.validation.ValidationResponseDTO;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ExternalValidationService {

    private final ClientRepository clientRepository;

    public ExternalValidationService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ValidationResponseDTO validateClient(ValidationRequestDTO request, double orderValue) {
        System.out.println(">>> [ValidationService] Validating clientId=" + request.getClientId()
                + ", name=" + request.getName() + ", email=" + request.getEmail()
                + ", orderValue=" + orderValue);

        // if value < 0 → INVALID
        if (orderValue < 0) {
            System.out.println(">>> [ValidationService] INVALID - Negative value not allowed");
            return new ValidationResponseDTO("INVALID", "Order value cannot be negative");
        }

        // verifies duplicate by name
        Client existingByName = clientRepository.findByName(request.getName());
        if (existingByName != null && !existingByName.getClientId().equals(request.getClientId())) {
            System.out.println(">>> [ValidationService] INVALID - Name already in use");
            return new ValidationResponseDTO("INVALID", "Name already in use");
        }

        // verifies duplicate by email
        clientRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            if (!existing.getClientId().equals(request.getClientId())) {
                throw new RuntimeException("INVALID: Email already in use");
            }
        });

        System.out.println(">>> [ValidationService] VALID");
        return new ValidationResponseDTO("VALID", "Client is valid");
    }
}



