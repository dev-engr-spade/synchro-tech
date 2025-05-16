package com.synchrotech.commandcenter.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.user.User;
import java.util.List;

/**
 * Repository for user entities.
 */
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByTenantId(String tenantId);
    // Add custom query methods if needed
} 