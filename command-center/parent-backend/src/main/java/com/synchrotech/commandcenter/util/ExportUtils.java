package com.synchrotech.commandcenter.util;

import java.io.FileWriter;
import java.io.IOException;
import com.synchrotech.commandcenter.exception.ServiceException;

/**
 * Utility methods for data export operations.
 */
public class ExportUtils {
    public static void exportToCsv(String filename, String data) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(data);
        } catch (IOException e) {
            throw new ServiceException("Export failed", e);
        }
    }
} 