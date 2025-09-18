package com.egitron_exercise.ordermanagement.service;

import com.egitron_exercise.ordermanagement.model.Client;
import com.egitron_exercise.ordermanagement.repository.ClientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public ClientDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found with email: " + email));

        return User.builder()
                .username(client.getEmail())
                .password(client.getPassword()) // BCrypt
                .roles("USER") // all clients have USER role
                .build();
    }
}
