package com.synchrotech.commandcenter.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role_audit_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleAuditLog {
    @Id
    private String id;
    private String roleId;
    private String action; // CREATE, UPDATE, DELETE, CLONE
    private String performedBy;
    private String tenantId;
    private String details;
    private Long timestamp;
} 