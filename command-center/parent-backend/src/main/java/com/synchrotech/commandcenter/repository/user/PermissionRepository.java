package com.synchrotech.commandcenter.repository.user;

import com.synchrotech.commandcenter.model.user.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for permission entities.
 */
public interface PermissionRepository extends MongoRepository<Permission, String> {
    Permission findByName(String name);
} 