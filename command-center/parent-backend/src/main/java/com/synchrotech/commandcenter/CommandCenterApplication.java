package com.synchrotech.commandcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the SynchroTech Command Center application.
 */
@SpringBootApplication
@EnableScheduling
public class CommandCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommandCenterApplication.class, args);
    }
} 