package com.synchrotech.commandcenter.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.List;

@Document(collection = "departments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    private String id;

    @Indexed
    private String name;

    private String tenantId;
    private String parentId; // For hierarchy
    private List<String> childIds;
    private String managerUserId;
    private DepartmentSettings settings;
    private Long createdAt;
    private Long updatedAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class DepartmentSettings {
    private String description;
    private boolean active;
    // Add more department-specific settings as needed
} 