package com.synchrotech.commandcenter.repository.audit;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.audit.AuditBase;

/**
 * Repository for audit base entities.
 */
public interface AuditBaseRepository extends MongoRepository<AuditBase, String> {
    // Add custom query methods if needed
} 