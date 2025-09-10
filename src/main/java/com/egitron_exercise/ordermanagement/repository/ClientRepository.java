package com.egitron_exercise.ordermanagement.repository;

import com.egitron_exercise.ordermanagement.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Métodos extra se precisares, ex:
    // Optional<Client> findByEmail(String email);
}
