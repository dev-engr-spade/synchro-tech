package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * Entity representing a calendar event.
 */
@Document(collection = "calendars")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {
    @Id
    private String id;
    private String tenantId;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private String resourceId;
    private String userId;
    private boolean allDay;
    private Date createdAt;
    private Date updatedAt;

    // Getters and setters
} 