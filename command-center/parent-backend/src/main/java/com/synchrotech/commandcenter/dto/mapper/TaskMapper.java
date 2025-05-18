package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Task;
import com.synchrotech.commandcenter.model.module.TaskStatus;
import com.synchrotech.commandcenter.model.module.TaskPriority;
import com.synchrotech.commandcenter.dto.request.module.TaskRequest;
import java.util.List;
import java.util.Map;

public class TaskMapper {
    public static Task toEntity(TaskRequest request) {
        if (request == null) return null;
        return Task.builder()
                .tenantId(request.getTenantId())
                .title(request.getTitle())
                .description(request.getDescription())
                .assigneeId(request.getAssigneeId())
                .reporterId(request.getReporterId())
                .projectId(request.getProjectId())
                .boardId(request.getBoardId())
                .status(request.getStatus() != null ? TaskStatus.valueOf(request.getStatus()) : null)
                .priority(request.getPriority() != null ? TaskPriority.valueOf(request.getPriority()) : null)
                .dueDate(request.getDueDate())
                .estimatedHours(request.getEstimatedHours())
                .actualHours(request.getActualHours())
                .labels(request.getLabels())
                .attachments(null)
                .comments(null)
                .customFields(request.getCustomFields())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 