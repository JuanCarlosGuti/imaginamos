package org.imaginemos.supplierservice.repository;

import org.imaginemos.supplierservice.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
