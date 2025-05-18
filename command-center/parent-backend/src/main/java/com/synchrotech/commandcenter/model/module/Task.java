package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    private String id;
    private String tenantId;
    private String title;
    private String description;
    private String assigneeId;
    private String reporterId;
    private String projectId;
    private String boardId;
    private TaskStatus status;
    private TaskPriority priority;
    private Date dueDate;
    private Integer estimatedHours;
    private Integer actualHours;
    private List<String> labels;
    private List<Attachment> attachments;
    private List<Comment> comments;
    private Map<String, Object> customFields;
    private Date createdAt;
    private Date updatedAt;
} 