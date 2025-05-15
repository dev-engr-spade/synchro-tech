package com.synchrotech.commandcenter.repository.tenant;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.tenant.Tenant;

/**
 * Repository for tenant entities.
 */
public interface TenantRepository extends MongoRepository<Tenant, String> {
    // Add custom query methods if needed
} 