package org.imaginemos.billingservice.repository;

import org.imaginemos.billingservice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
