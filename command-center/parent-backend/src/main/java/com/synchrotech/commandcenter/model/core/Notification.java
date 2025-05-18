package com.synchrotech.commandcenter.model.core;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * Entity representing a notification.
 */
@Document(collection = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    private String id;
    private String tenantId;
    private String userId;
    private String message;
    private String type;
    private boolean read;
    private Date sentAt;
    private Date createdAt;
    private Date updatedAt;

    // Getters and setters
} 