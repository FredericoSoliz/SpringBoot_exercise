package com.egitron_exercise.ordermanagement.external.validation;

import com.egitron_exercise.ordermanagement.service.ExternalValidationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/external")
public class ExternalValidationController {

    private final ExternalValidationService externalValidationService;

    public ExternalValidationController(ExternalValidationService externalValidationService) {
        this.externalValidationService = externalValidationService;
    }

    @PostMapping("/validate")
    public ValidationResponseDTO validateClient(@RequestBody ValidationRequestDTO request, double orderValue) {
        return validateClient(request, 0.0); // default orderValue = 0
    }
}


