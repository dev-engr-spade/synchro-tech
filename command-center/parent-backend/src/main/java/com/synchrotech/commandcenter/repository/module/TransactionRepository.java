package com.synchrotech.commandcenter.repository.module;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.module.Transaction;

/**
 * Repository for transaction entities.
 */
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    // Add custom query methods if needed
} 