package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.Role;
import com.synchrotech.commandcenter.model.user.Permission;
import com.synchrotech.commandcenter.model.user.RoleAuditLog;
import com.synchrotech.commandcenter.repository.user.RoleRepository;
import com.synchrotech.commandcenter.repository.user.PermissionRepository;
import com.synchrotech.commandcenter.repository.user.RoleAuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Date;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleAuditLogRepository roleAuditLogRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository, RoleAuditLogRepository roleAuditLogRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.roleAuditLogRepository = roleAuditLogRepository;
    }

    @Override
    public Role createRole(Role role) {
        Role saved = roleRepository.save(role);
        roleAuditLogRepository.save(RoleAuditLog.builder()
                .roleId(saved.getId())
                .action("CREATE")
                .performedBy("system")
                .tenantId(saved.getTenantId())
                .details("Created role: " + saved.getName())
                .timestamp(System.currentTimeMillis())
                .build());
        return saved;
    }

    @Override
    public Role updateRole(String id, Role role) {
        role.setId(id);
        Role saved = roleRepository.save(role);
        roleAuditLogRepository.save(RoleAuditLog.builder()
                .roleId(saved.getId())
                .action("UPDATE")
                .performedBy("system")
                .tenantId(saved.getTenantId())
                .details("Updated role: " + saved.getName())
                .timestamp(System.currentTimeMillis())
                .build());
        return saved;
    }

    @Override
    public void deleteRole(String id) {
        Role role = roleRepository.findById(id).orElse(null);
        roleRepository.deleteById(id);
        if (role != null) {
            roleAuditLogRepository.save(RoleAuditLog.builder()
                    .roleId(role.getId())
                    .action("DELETE")
                    .performedBy("system")
                    .tenantId(role.getTenantId())
                    .details("Deleted role: " + role.getName())
                    .timestamp(System.currentTimeMillis())
                    .build());
        }
    }

    public Role cloneRole(Role source, String newName, String description, String tenantId) {
        Role clone = Role.builder()
                .name(newName)
                .description(description)
                .permissions(source.getPermissions())
                .tenantId(tenantId)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        Role saved = roleRepository.save(clone);
        roleAuditLogRepository.save(RoleAuditLog.builder()
                .roleId(saved.getId())
                .action("CLONE")
                .performedBy("system")
                .tenantId(saved.getTenantId())
                .details("Cloned from role: " + source.getId())
                .timestamp(System.currentTimeMillis())
                .build());
        return saved;
    }

    @Override
    public Role findRoleById(String id) { return roleRepository.findById(id).orElse(null); }
    @Override
    public List<Role> findAllRoles() { return roleRepository.findAll(); }

    @Override
    public Permission createPermission(Permission permission) { return permissionRepository.save(permission); }
    @Override
    public Permission updatePermission(String id, Permission permission) {
        permission.setId(id);
        return permissionRepository.save(permission);
    }
    @Override
    public void deletePermission(String id) { permissionRepository.deleteById(id); }
    @Override
    public Permission findPermissionById(String id) { return permissionRepository.findById(id).orElse(null); }
    @Override
    public List<Permission> findAllPermissions() { return permissionRepository.findAll(); }
} 