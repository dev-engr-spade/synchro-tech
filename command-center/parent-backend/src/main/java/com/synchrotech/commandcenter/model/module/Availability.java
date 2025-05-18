package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "availabilities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Availability {
    @Id
    private String id;
    private String tenantId;
    private String resourceId;
    private Date startTime;
    private Date endTime;
    private boolean available;
    private Date createdAt;
    private Date updatedAt;
} 