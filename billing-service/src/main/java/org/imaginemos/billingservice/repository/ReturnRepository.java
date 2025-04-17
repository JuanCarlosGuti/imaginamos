package org.imaginemos.billingservice.repository;

import org.imaginemos.billingservice.model.Return;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRepository extends JpaRepository<Return, Long> {
}
