package org.imaginemos.clientservice.repository;

import org.imaginemos.clientservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}