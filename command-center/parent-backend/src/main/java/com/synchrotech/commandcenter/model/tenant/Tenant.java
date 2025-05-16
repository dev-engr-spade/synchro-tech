package com.synchrotech.commandcenter.model.tenant;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.List;

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

    private TenantSettings settings;

    private Branding branding;

    private List<String> enabledFeatures;

    private boolean active;

    private Long createdAt;
    private Long updatedAt;

    private String tenantId; // For shared collection multi-tenancy
} 