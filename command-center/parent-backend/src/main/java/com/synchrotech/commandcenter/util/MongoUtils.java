package com.synchrotech.commandcenter.util;

/**
 * Utility methods for MongoDB operations.
 */
public class MongoUtils {
    public static String getTenantCollectionName(String base, String tenantId) {
        return base + "_" + tenantId;
    }

    // Add MongoDB utility methods here
} 