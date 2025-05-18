package com.synchrotech.commandcenter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoDB configuration.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.synchrotech.commandcenter.repository")
public class MongoConfig {
    // Configure MongoDB beans here
} 