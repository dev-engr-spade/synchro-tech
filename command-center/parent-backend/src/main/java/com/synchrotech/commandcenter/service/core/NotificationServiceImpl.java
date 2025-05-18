package com.synchrotech.commandcenter.service.core;

import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    public void sendNotification(String tenantId, String userId, String message) {
        System.out.printf("Notification to user %s in tenant %s: %s%n", userId, tenantId, message);
    }
} 