package com.synchrotech.commandcenter.repository.tenant;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.tenant.TenantSettings;

/**
 * Repository for tenant settings entities.
 */
public interface TenantSettingsRepository extends MongoRepository<TenantSettings, String> {
    // Add custom query methods if needed
} 