package com.synchrotech.commandcenter.repository.module;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.module.Customer;

/**
 * Repository for customer entities.
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {
    // Add custom query methods if needed
} 