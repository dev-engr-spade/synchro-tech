package com.synchrotech.commandcenter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synchrotech.commandcenter.exception.ServiceException;

/**
 * Utility methods for JSON operations.
 */
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new ServiceException("JSON serialization failed", e);
        }
    }
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new ServiceException("JSON deserialization failed", e);
        }
    }
} 