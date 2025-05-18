package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Resource;
import com.synchrotech.commandcenter.dto.request.module.ResourceRequest;

public class ResourceMapper {
    public static Resource toEntity(ResourceRequest request) {
        if (request == null) return null;
        return Resource.builder()
                .tenantId(request.getTenantId())
                .name(request.getName())
                .type(request.getType())
                .available(request.isAvailable())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 