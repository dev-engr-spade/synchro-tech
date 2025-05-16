package com.synchrotech.commandcenter.controller;

import com.synchrotech.commandcenter.model.user.Role;
import com.synchrotech.commandcenter.model.user.Permission;
import com.synchrotech.commandcenter.service.user.RoleService;
import com.synchrotech.commandcenter.service.user.UserService;
import com.synchrotech.commandcenter.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Role management endpoints.
 */
@RestController
@RequestMapping("/api/admin/roles")
public class RoleController {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) { return roleService.createRole(role); }
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable String id, @RequestBody Role role) { return roleService.updateRole(id, role); }
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
        user.setRoleIds(roleIds);
        // Add audit entry
        if (user.getRoleAssignmentAudit() == null) user.setRoleAssignmentAudit(new java.util.ArrayList<>());
        user.getRoleAssignmentAudit().add("Roles set to: " + roleIds + " at " + System.currentTimeMillis());
        return userService.updateUser(userId, user);
    }
} 