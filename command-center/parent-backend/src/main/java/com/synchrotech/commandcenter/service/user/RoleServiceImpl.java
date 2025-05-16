package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.Role;
import com.synchrotech.commandcenter.model.user.Permission;
import com.synchrotech.commandcenter.repository.user.RoleRepository;
import com.synchrotech.commandcenter.repository.user.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Role createRole(Role role) { return roleRepository.save(role); }
    @Override
    public Role updateRole(String id, Role role) {
        role.setId(id);
        return roleRepository.save(role);
    }
    @Override
    public void deleteRole(String id) { roleRepository.deleteById(id); }
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