package com.synchrotech.commandcenter.repository.core;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.core.Notification;

/**
 * Repository for notification entities.
 */
public interface NotificationRepository extends MongoRepository<Notification, String> {
    // Add custom query methods if needed
} 