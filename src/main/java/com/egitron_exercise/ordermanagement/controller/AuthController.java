package com.egitron_exercise.ordermanagement.controller;

import com.egitron_exercise.ordermanagement.dto.ClientRequestDTO;
import com.egitron_exercise.ordermanagement.dto.auth.AuthRequestDTO;
import com.egitron_exercise.ordermanagement.dto.auth.AuthResponseDTO;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import com.egitron_exercise.ordermanagement.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService,
                          ClientRepository clientRepository,
                          PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ClientRequestDTO request) {
        Optional<Client> existing = clientRepository.findByEmail(request.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        Client client = new Client();
        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPassword(passwordEncoder.encode(request.getPassword()));
        client.setCreatedAt(LocalDateTime.now());

        clientRepository.save(client);
        return ResponseEntity.ok("Client registered successfully");
    }

    // LOGIN
    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return new AuthResponseDTO(token);
    }
}
