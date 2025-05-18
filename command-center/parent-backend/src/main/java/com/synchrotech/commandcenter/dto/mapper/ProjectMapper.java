package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Project;
import com.synchrotech.commandcenter.model.module.ProjectStatus;
import com.synchrotech.commandcenter.dto.request.module.ProjectRequest;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {
    public static Project toEntity(ProjectRequest request) {
        if (request == null) return null;
        return Project.builder()
                .tenantId(request.getTenantId())
                .name(request.getName())
                .description(request.getDescription())
                .managerId(request.getManagerId())
                .teamMembers(request.getTeamMembers())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus() != null ? ProjectStatus.valueOf(request.getStatus()) : null)
                .budget(request.getBudget())
                .actualCost(request.getActualCost())
                .customFields(request.getCustomFields())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 