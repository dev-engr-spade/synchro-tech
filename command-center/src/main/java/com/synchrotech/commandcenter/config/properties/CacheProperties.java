package com.synchrotech.commandcenter.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Cache properties.
 */
@Configuration
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {
    // Define cache-specific properties here
} 