package com.synchrotech.commandcenter.exception;

/**
 * Exception for conflict scenarios.
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
} 