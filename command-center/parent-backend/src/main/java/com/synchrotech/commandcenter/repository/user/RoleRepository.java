package com.synchrotech.commandcenter.repository.user;

import com.synchrotech.commandcenter.model.user.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for role entities.
 */
public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String name);
} 