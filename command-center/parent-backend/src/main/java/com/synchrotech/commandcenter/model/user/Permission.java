package com.synchrotech.commandcenter.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Entity representing a permission.
 */
@Document(collection = "permissions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    private String id;

    @Indexed
    private String name;
    private String description;
    private String tenantId;
    // Add other permission fields

    // Getters and setters
} 