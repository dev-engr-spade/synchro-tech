package com.synchrotech.commandcenter.repository.user;

import com.synchrotech.commandcenter.model.user.RoleAuditLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RoleAuditLogRepository extends MongoRepository<RoleAuditLog, String> {
    List<RoleAuditLog> findByRoleId(String roleId);
    List<RoleAuditLog> findByTenantId(String tenantId);
} 