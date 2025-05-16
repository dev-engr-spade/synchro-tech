package com.synchrotech.commandcenter.repository.tenant;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.tenant.Tenant;
import java.util.Optional;

/**
 * Repository for tenant entities.
 */
public interface TenantRepository extends MongoRepository<Tenant, String> {
    Optional<Tenant> findBySubdomain(String subdomain);
    // Add custom query methods if needed
} 