package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Appointment;
import com.synchrotech.commandcenter.dto.request.module.AppointmentRequest;

public class AppointmentMapper {
    public static Appointment toEntity(AppointmentRequest request) {
        if (request == null) return null;
        return Appointment.builder()
                .tenantId(request.getTenantId())
                .title(request.getTitle())
                .description(request.getDescription())
                .resourceId(request.getResourceId())
                .userId(request.getUserId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .confirmed(request.isConfirmed())
                .cancelled(request.isCancelled())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 