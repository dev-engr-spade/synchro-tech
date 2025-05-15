package com.synchrotech.commandcenter.repository.module;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.module.Inventory;

/**
 * Repository for inventory entities.
 */
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    // Add custom query methods if needed
} 