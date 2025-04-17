package org.imaginemos.inventoryservice.repository;

import org.imaginemos.inventoryservice.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByNombreIgnoreCase(String nombre);

}
