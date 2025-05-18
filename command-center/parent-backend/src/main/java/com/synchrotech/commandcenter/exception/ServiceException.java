package com.synchrotech.commandcenter.exception;

public class ServiceException extends ApiException {
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
} 