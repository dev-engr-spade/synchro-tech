package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Calendar;
import com.synchrotech.commandcenter.dto.request.module.CalendarRequest;

public class CalendarMapper {
    public static Calendar toEntity(CalendarRequest request) {
        if (request == null) return null;
        return Calendar.builder()
                .tenantId(request.getTenantId())
                .title(request.getTitle())
                .description(request.getDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .resourceId(request.getResourceId())
                .userId(request.getUserId())
                .allDay(request.isAllDay())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 