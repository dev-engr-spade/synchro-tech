package com.synchrotech.commandcenter.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.Date;

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
    private Date createdAt;
    private Date updatedAt;
    // Add other permission fields

    // Getters and setters
} 