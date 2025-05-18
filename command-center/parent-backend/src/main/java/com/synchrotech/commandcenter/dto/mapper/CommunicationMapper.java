package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Communication;
import com.synchrotech.commandcenter.dto.request.module.CommunicationRequest;

public class CommunicationMapper {
    public static Communication toEntity(CommunicationRequest request) {
        if (request == null) return null;
        return Communication.builder()
                .tenantId(request.getTenantId())
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .subject(request.getSubject())
                .content(request.getContent())
                .type(request.getType())
                .status(request.getStatus())
                .sentAt(request.getSentAt())
                .readAt(request.getReadAt())
                .attachments(request.getAttachments())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 