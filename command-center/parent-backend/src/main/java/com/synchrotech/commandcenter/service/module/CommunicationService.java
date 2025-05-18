package com.synchrotech.commandcenter.service.module;

public interface CommunicationService {
    void sendMessage(String tenantId, String userId, String message);
} 