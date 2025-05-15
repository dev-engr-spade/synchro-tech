package com.synchrotech.commandcenter.repository.module;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.module.Calendar;

/**
 * Repository for calendar entities.
 */
public interface CalendarRepository extends MongoRepository<Calendar, String> {
    // Add custom query methods if needed
} 