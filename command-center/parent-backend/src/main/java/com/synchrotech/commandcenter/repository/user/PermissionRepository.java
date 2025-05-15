package com.synchrotech.commandcenter.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.user.Permission;

/**
 * Repository for permission entities.
 */
public interface PermissionRepository extends MongoRepository<Permission, String> {
    // Add custom query methods if needed
} 