package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.Role;
import com.synchrotech.commandcenter.model.user.Permission;
import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    Role updateRole(String id, Role role);
    void deleteRole(String id);
    Role findRoleById(String id);
    List<Role> findAllRoles();

    Permission createPermission(Permission permission);
    Permission updatePermission(String id, Permission permission);
    void deletePermission(String id);
    Permission findPermissionById(String id);
    List<Permission> findAllPermissions();
} 