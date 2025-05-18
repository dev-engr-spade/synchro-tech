package com.synchrotech.commandcenter.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
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
    private String tenantId; // null for system roles
    private String name;
    private String description;
    private List<String> permissions;
    private Date createdAt;
    private Date updatedAt;
} 