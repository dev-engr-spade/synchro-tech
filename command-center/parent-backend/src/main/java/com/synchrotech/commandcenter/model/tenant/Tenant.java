package com.synchrotech.commandcenter.model.tenant;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "tenants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @Indexed(unique = true)
    private String subdomain;

    private String industry;

    private Map<String, Object> settings;

    private ThemeSettings theme;

    private List<String> features;

    private boolean active;

    private Date createdAt;
    private Date updatedAt;

    private String tenantId; // For shared collection multi-tenancy
} 