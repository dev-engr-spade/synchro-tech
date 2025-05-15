package com.synchrotech.commandcenter.repository.module;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.module.Communication;

/**
 * Repository for communication entities.
 */
public interface CommunicationRepository extends MongoRepository<Communication, String> {
    // Add custom query methods if needed
} 