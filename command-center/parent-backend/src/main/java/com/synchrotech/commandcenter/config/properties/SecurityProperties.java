package com.synchrotech.commandcenter.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Security properties.
 */
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    // Define security-specific properties here
} 