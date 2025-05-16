package com.synchrotech.commandcenter.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.List;

/**
 * Entity representing a role.
 */
@Document(collection = "roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private String id;

    @Indexed
    private String name;
    private String description;
    private List<String> permissionIds;
    private String tenantId;
    private String clonedFromRoleId;
    private Long createdAt;
    private Long updatedAt;
    private String createdBy;
    private String updatedBy;

    // Getters and setters
} 