package com.egitron_exercise.ordermanagement.service;

import com.egitron_exercise.ordermanagement.jwt.JWTService;
import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(String email, String password) {
        Optional<Client> clientOpt = clientRepository.findByEmail(email);

        if (clientOpt.isEmpty() || !passwordEncoder.matches(password, clientOpt.get().getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        Client client = clientOpt.get();
        return jwtService.generateToken(client.getEmail());
    }
}
