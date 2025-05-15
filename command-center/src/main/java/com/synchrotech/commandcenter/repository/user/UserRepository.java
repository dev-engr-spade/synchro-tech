package com.synchrotech.commandcenter.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.user.User;

/**
 * Repository for user entities.
 */
public interface UserRepository extends MongoRepository<User, String> {
    // Add custom query methods if needed
} 