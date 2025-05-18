package com.synchrotech.commandcenter.controller;

import com.synchrotech.commandcenter.model.user.Role;
import com.synchrotech.commandcenter.model.user.Permission;
import com.synchrotech.commandcenter.model.user.RoleAuditLog;
import com.synchrotech.commandcenter.service.user.RoleService;
import com.synchrotech.commandcenter.service.user.UserService;
import com.synchrotech.commandcenter.model.user.User;
import com.synchrotech.commandcenter.dto.request.user.RoleCreateRequest;
import com.synchrotech.commandcenter.dto.request.user.RoleUpdateRequest;
import com.synchrotech.commandcenter.dto.request.user.RoleCloneRequest;
import com.synchrotech.commandcenter.repository.user.RoleAuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Date;

/**
 * Role management endpoints.
 */
@RestController
@RequestMapping("/api/admin/roles")
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;
    private final RoleAuditLogRepository roleAuditLogRepository;

    @Autowired
    public RoleController(RoleService roleService, UserService userService, RoleAuditLogRepository roleAuditLogRepository) {
        this.roleService = roleService;
        this.userService = userService;
        this.roleAuditLogRepository = roleAuditLogRepository;
    }

    @PostMapping
    public Role createRole(@RequestBody RoleCreateRequest request) {
        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .permissions(request.getPermissionIds())
                .tenantId(request.getTenantId())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return roleService.createRole(role);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable String id, @RequestBody RoleUpdateRequest request) {
        Role role = Role.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .permissions(request.getPermissionIds())
                .updatedAt(new Date())
                .build();
        return roleService.updateRole(id, role);
    }

    @Operation(summary = "Clone a role", description = "Clone an existing role to a new role")
    @ApiResponse(responseCode = "200", description = "Role cloned successfully")
    @PostMapping("/clone")
    public Role cloneRole(@RequestBody RoleCloneRequest request) {
        Role source = roleService.findRoleById(request.getSourceRoleId());
        return ((com.synchrotech.commandcenter.service.user.RoleServiceImpl)roleService)
            .cloneRole(source, request.getNewName(), request.getDescription(), request.getTenantId());
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable String id) { roleService.deleteRole(id); }
    @GetMapping
    public List<Role> getAllRoles() { return roleService.findAllRoles(); }
    @GetMapping("/{id}")
    public Role getRole(@PathVariable String id) { return roleService.findRoleById(id); }

    @PostMapping("/permissions")
    public Permission createPermission(@RequestBody Permission permission) { return roleService.createPermission(permission); }
    @PutMapping("/permissions/{id}")
    public Permission updatePermission(@PathVariable String id, @RequestBody Permission permission) { return roleService.updatePermission(id, permission); }
    @DeleteMapping("/permissions/{id}")
    public void deletePermission(@PathVariable String id) { roleService.deletePermission(id); }
    @GetMapping("/permissions")
    public List<Permission> getAllPermissions() { return roleService.findAllPermissions(); }
    @GetMapping("/permissions/{id}")
    public Permission getPermission(@PathVariable String id) { return roleService.findPermissionById(id); }

    @PutMapping("/assign/{userId}")
    public User assignRolesToUser(@PathVariable String userId, @RequestBody List<String> roleIds) {
        User user = userService.findById(userId).orElseThrow();
        user.setRoles(roleIds);
        if (user.getRoleAssignmentAudit() == null) user.setRoleAssignmentAudit(new java.util.ArrayList<>());
        user.getRoleAssignmentAudit().add("Roles set to: " + roleIds + " at " + new Date());
        return userService.updateUser(userId, user);
    }

    @Operation(summary = "Get audit logs for a role", description = "Retrieve audit logs for a specific role")
    @ApiResponse(responseCode = "200", description = "Audit logs returned")
    @GetMapping("/audit/{roleId}")
    public List<RoleAuditLog> getAuditLogsByRole(@Parameter(description = "Role ID") @PathVariable String roleId) {
        return roleAuditLogRepository.findByRoleId(roleId);
    }

    @Operation(summary = "Get audit logs for a tenant", description = "Retrieve audit logs for all roles in a tenant")
    @ApiResponse(responseCode = "200", description = "Audit logs returned")
    @GetMapping("/audit/tenant/{tenantId}")
    public List<RoleAuditLog> getAuditLogsByTenant(@Parameter(description = "Tenant ID") @PathVariable String tenantId) {
        return roleAuditLogRepository.findByTenantId(tenantId);
    }
} 