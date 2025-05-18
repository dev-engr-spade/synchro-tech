package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * Entity representing a customer.
 */
@Document(collection = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private String id;
    private String tenantId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;

    // Getters and setters
} 