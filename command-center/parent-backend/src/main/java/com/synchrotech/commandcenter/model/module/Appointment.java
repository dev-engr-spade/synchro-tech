package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "appointments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    private String id;
    private String tenantId;
    private String title;
    private String description;
    private String resourceId;
    private String userId;
    private Date startTime;
    private Date endTime;
    private boolean confirmed;
    private boolean cancelled;
    private Date createdAt;
    private Date updatedAt;
} 