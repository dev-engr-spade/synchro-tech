package com.synchrotech.commandcenter.util;

import java.io.File;

/**
 * Utility methods for file operations.
 */
public class FileUtils {
    public static boolean deleteFile(File file) {
        return file != null && file.exists() && file.delete();
    }

    // Add file utility methods here
} 