package com.synchrotech.commandcenter.model.module;

import lombok.*;
import java.util.Date;
import java.util.List;

/**
 * Entity representing a communication message.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Communication {
    private String id;
    private String tenantId;
    private String senderId;
    private String receiverId;
    private String subject;
    private String content;
    private String type;
    private String status;
    private Date sentAt;
    private Date readAt;
    private List<String> attachments;
    private Date createdAt;
    private Date updatedAt;

    // Getters and setters
} 