package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "customer_groups")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGroup {
    @Id
    private String id;
    private String tenantId;
    private String name;
    private String description;
    private List<String> customerIds;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;
} 