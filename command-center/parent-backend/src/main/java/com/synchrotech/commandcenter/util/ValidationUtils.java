package com.synchrotech.commandcenter.util;

public class ValidationUtils {
    public static void requireNonNull(Object obj, String message) {
        if (obj == null) throw new IllegalArgumentException(message);
    }
    public static void requireTrue(boolean condition, String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }
} 