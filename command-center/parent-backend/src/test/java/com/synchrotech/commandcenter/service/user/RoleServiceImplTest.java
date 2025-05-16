package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.Role;
import com.synchrotech.commandcenter.model.user.Permission;
import com.synchrotech.commandcenter.model.user.RoleAuditLog;
import com.synchrotech.commandcenter.repository.user.RoleRepository;
import com.synchrotech.commandcenter.repository.user.PermissionRepository;
import com.synchrotech.commandcenter.repository.user.RoleAuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {
    @Mock RoleRepository roleRepository;
    @Mock PermissionRepository permissionRepository;
    @Mock RoleAuditLogRepository roleAuditLogRepository;
    @InjectMocks RoleServiceImpl roleService;
    Role role;

    @BeforeEach
    void setup() {
        role = Role.builder().id("role1").name("Manager").tenantId("tenant1").build();
    }

    @Test
    void createRole_shouldSaveAndAudit() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        Role created = roleService.createRole(role);
        assertEquals("Manager", created.getName());
        verify(roleAuditLogRepository).save(any(RoleAuditLog.class));
    }

    @Test
    void updateRole_shouldUpdateAndAudit() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        Role updated = roleService.updateRole("role1", role);
        assertEquals("Manager", updated.getName());
        verify(roleAuditLogRepository).save(any(RoleAuditLog.class));
    }

    @Test
    void deleteRole_shouldDeleteAndAudit() {
        when(roleRepository.findById("role1")).thenReturn(Optional.of(role));
        doNothing().when(roleRepository).deleteById("role1");
        roleService.deleteRole("role1");
        verify(roleRepository).deleteById("role1");
        verify(roleAuditLogRepository).save(any(RoleAuditLog.class));
    }

    @Test
    void cloneRole_shouldCloneAndAudit() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        Role cloned = roleService.cloneRole(role, "Clone", "desc", "tenant1");
        assertEquals("Clone", cloned.getName());
        verify(roleAuditLogRepository).save(any(RoleAuditLog.class));
    }

    @Test
    void findRoleById_shouldReturnRole() {
        when(roleRepository.findById("role1")).thenReturn(Optional.of(role));
        Role found = roleService.findRoleById("role1");
        assertNotNull(found);
    }

    @Test
    void findAllRoles_shouldReturnList() {
        when(roleRepository.findAll()).thenReturn(List.of(role));
        List<Role> list = roleService.findAllRoles();
        assertEquals(1, list.size());
    }

    @Test
    void createPermission_shouldSave() {
        Permission perm = Permission.builder().id("perm1").name("READ").build();
        when(permissionRepository.save(any(Permission.class))).thenReturn(perm);
        Permission created = roleService.createPermission(perm);
        assertEquals("READ", created.getName());
    }

    @Test
    void updatePermission_shouldUpdate() {
        Permission perm = Permission.builder().id("perm1").name("WRITE").build();
        when(permissionRepository.save(any(Permission.class))).thenReturn(perm);
        Permission updated = roleService.updatePermission("perm1", perm);
        assertEquals("WRITE", updated.getName());
    }

    @Test
    void deletePermission_shouldDelete() {
        doNothing().when(permissionRepository).deleteById("perm1");
        roleService.deletePermission("perm1");
        verify(permissionRepository).deleteById("perm1");
    }

    @Test
    void findPermissionById_shouldReturnPermission() {
        Permission perm = Permission.builder().id("perm1").name("READ").build();
        when(permissionRepository.findById("perm1")).thenReturn(Optional.of(perm));
        Permission found = roleService.findPermissionById("perm1");
        assertNotNull(found);
    }

    @Test
    void findAllPermissions_shouldReturnList() {
        Permission perm = Permission.builder().id("perm1").name("READ").build();
        when(permissionRepository.findAll()).thenReturn(List.of(perm));
        List<Permission> list = roleService.findAllPermissions();
        assertEquals(1, list.size());
    }
} 