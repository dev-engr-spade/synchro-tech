package com.synchrotech.commandcenter.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.user.Role;

/**
 * Repository for role entities.
 */
public interface RoleRepository extends MongoRepository<Role, String> {
    // Add custom query methods if needed
} 