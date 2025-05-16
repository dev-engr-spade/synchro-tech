package com.synchrotech.commandcenter.service.module;

import com.synchrotech.commandcenter.model.module.Inventory;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for inventory module operations.
 */
public interface InventoryService {
    Inventory createInventory(Inventory inventory);
    Inventory updateInventory(String id, Inventory inventory);
    Optional<Inventory> findById(String id);
    List<Inventory> findByTenantId(String tenantId);
    void deleteInventory(String id);
} 