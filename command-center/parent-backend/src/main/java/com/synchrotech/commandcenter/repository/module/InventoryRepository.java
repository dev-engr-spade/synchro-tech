package com.synchrotech.commandcenter.repository.module;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.module.Inventory;
import java.util.List;

/**
 * Repository for inventory entities.
 */
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    List<Inventory> findByTenantId(String tenantId);
    // Add custom query methods if needed
} 