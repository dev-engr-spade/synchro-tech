package com.synchrotech.commandcenter.util;

public class StringUtils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    public static String capitalize(String str) {
        if (isNullOrEmpty(str)) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
} 