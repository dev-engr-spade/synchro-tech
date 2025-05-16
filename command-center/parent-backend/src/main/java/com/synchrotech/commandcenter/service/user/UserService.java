package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.User;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for user operations.
 */
public interface UserService {
    User createUser(User user);
    User updateUser(String id, User user);
    Optional<User> findById(String id);
    List<User> findByTenantId(String tenantId);
    void deleteUser(String id);
} 