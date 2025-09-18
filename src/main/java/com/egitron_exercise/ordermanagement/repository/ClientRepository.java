package com.egitron_exercise.ordermanagement.repository;

import com.egitron_exercise.ordermanagement.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByName(String name);
    Optional<Client> findByEmail(String email);




}
