package com.egitron_exercise.ordermanagement.controller;

import com.egitron_exercise.ordermanagement.dto.auth.AuthRequestDTO;
import com.egitron_exercise.ordermanagement.dto.auth.AuthResponseDTO;
import com.egitron_exercise.ordermanagement.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        return new AuthResponseDTO(token);
    }
}
