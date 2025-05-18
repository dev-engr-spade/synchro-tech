package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Entity representing a project.
 */
@Document(collection = "projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private String id;
    private String tenantId;
    private String name;
    private String description;
    private String managerId;
    private List<String> teamMembers;
    private Date startDate;
    private Date endDate;
    private ProjectStatus status;
    private BigDecimal budget;
    private BigDecimal actualCost;
    private Map<String, Object> customFields;
    private Date createdAt;
    private Date updatedAt;

    // Getters and setters
} 