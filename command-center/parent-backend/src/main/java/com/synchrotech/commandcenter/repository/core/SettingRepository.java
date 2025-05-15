package com.synchrotech.commandcenter.repository.core;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.core.Setting;

/**
 * Repository for setting entities.
 */
public interface SettingRepository extends MongoRepository<Setting, String> {
    // Add custom query methods if needed
} 