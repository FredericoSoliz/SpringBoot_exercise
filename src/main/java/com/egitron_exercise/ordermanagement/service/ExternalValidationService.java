package com.egitron_exercise.ordermanagement.service;

import com.egitron_exercise.ordermanagement.external.validation.ValidationRequestDTO;
import com.egitron_exercise.ordermanagement.external.validation.ValidationResponseDTO;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ExternalValidationService {

    private final ClientRepository clientRepository;
    private final ErrorLogService errorLogService;
    private final EmailService emailService;

    public ExternalValidationService(ClientRepository clientRepository,
                                     ErrorLogService errorLogService,
                                     EmailService emailService) {
        this.clientRepository = clientRepository;
        this.errorLogService = errorLogService;
        this.emailService = emailService;
    }

    public ValidationResponseDTO validateClient(ValidationRequestDTO request, double orderValue) {
        System.out.println(">>> [ValidationService] Validating clientId=" + request.getClientId()
                + ", name=" + request.getName() + ", email=" + request.getEmail()
                + ", orderValue=" + orderValue);

        // if value < 0 → INVALID
        if (orderValue < 0) {
            String msg = "INVALID - Negative value not allowed";
            System.out.println(">>> [ValidationService] " + msg);
            errorLogService.logError(msg);
            emailService.sendErrorToUser(request.getEmail(), msg);
            return new ValidationResponseDTO("INVALID", "Order value cannot be negative");
        }

        // verifies duplicate by name
        Client existingByName = clientRepository.findByName(request.getName());
        if (existingByName != null && !existingByName.getClientId().equals(request.getClientId())) {
            String msg = "INVALID - Name already in use";
            System.out.println(">>> [ValidationService] " + msg);
            errorLogService.logError(msg);
            emailService.sendErrorToUser(request.getEmail(), msg);
            return new ValidationResponseDTO("INVALID", "Name already in use");
        }

        // verifies duplicate by email
        clientRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
            if (!existing.getClientId().equals(request.getClientId())) {
                String msg = "INVALID: Email already in use";
                System.out.println(">>> [ValidationService] " + msg);
                errorLogService.logError(msg);
                emailService.sendErrorToUser(request.getEmail(), msg);
                throw new RuntimeException(msg);
            }
        });

        System.out.println(">>> [ValidationService] VALID");
        return new ValidationResponseDTO("VALID", "Client is valid");
    }
}





