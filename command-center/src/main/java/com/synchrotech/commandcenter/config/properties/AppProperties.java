package com.synchrotech.commandcenter.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application properties.
 */
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    // Define application-specific properties here
} 