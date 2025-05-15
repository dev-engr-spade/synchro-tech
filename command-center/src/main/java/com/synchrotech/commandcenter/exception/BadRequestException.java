package com.synchrotech.commandcenter.exception;

/**
 * Exception for bad request scenarios.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
} 