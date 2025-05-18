package com.synchrotech.commandcenter.service.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synchrotech.commandcenter.model.core.Notification;
import com.synchrotech.commandcenter.repository.core.NotificationRepository;
import java.util.Date;

@Service
public class CommunicationServiceImpl implements CommunicationService {
    private final NotificationRepository notificationRepository;
    @Autowired
    public CommunicationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    @Override
    public void sendMessage(String tenantId, String userId, String message) {
        Notification notification = Notification.builder()
                .tenantId(tenantId)
                .userId(userId)
                .message(message)
                .type("IN_APP")
                .read(false)
                .sentAt(new Date())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        notificationRepository.save(notification);
    }
} 