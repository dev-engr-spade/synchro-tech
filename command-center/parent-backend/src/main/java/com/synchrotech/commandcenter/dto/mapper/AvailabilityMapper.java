package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Availability;
import com.synchrotech.commandcenter.dto.request.module.AvailabilityRequest;

public class AvailabilityMapper {
    public static Availability toEntity(AvailabilityRequest request) {
        if (request == null) return null;
        return Availability.builder()
                .tenantId(request.getTenantId())
                .resourceId(request.getResourceId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .available(request.isAvailable())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 