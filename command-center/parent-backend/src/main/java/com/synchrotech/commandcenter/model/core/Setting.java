package com.synchrotech.commandcenter.model.core;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity representing a system setting.
 */
@Document(collection = "settings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Setting {
    @Id
    private String id;
    private String tenantId;
    private String key;
    private String value;
    private String description;
    private boolean active;
    // Add other setting fields

    // Getters and setters
} 