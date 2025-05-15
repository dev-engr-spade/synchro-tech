package com.synchrotech.commandcenter.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MongoDB properties.
 */
@Configuration
@ConfigurationProperties(prefix = "mongo")
public class MongoProperties {
    // Define MongoDB-specific properties here
} 