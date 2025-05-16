package com.synchrotech.commandcenter.controller;

import com.synchrotech.commandcenter.model.user.Role;
import com.synchrotech.commandcenter.model.user.Permission;
import com.synchrotech.commandcenter.model.user.RoleAuditLog;
import com.synchrotech.commandcenter.model.user.User;
import com.synchrotech.commandcenter.dto.request.user.RoleCreateRequest;
import com.synchrotech.commandcenter.dto.request.user.RoleUpdateRequest;
import com.synchrotech.commandcenter.dto.request.user.RoleCloneRequest;
import com.synchrotech.commandcenter.service.user.RoleService;
import com.synchrotech.commandcenter.service.user.UserService;
import com.synchrotech.commandcenter.repository.user.RoleAuditLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.ArrayList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
public class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoleService roleService;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleAuditLogRepository roleAuditLogRepository;

    @Test
    void createRole_shouldReturnRole() throws Exception {
        RoleCreateRequest req = new RoleCreateRequest();
        req.setName("Manager");
        req.setDescription("desc");
        req.setTenantId("tenant1");
        Role role = Role.builder().id("role1").name("Manager").description("desc").tenantId("tenant1").build();
        Mockito.when(roleService.createRole(Mockito.any(Role.class))).thenReturn(role);
        mockMvc.perform(post("/api/admin/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\":\"Manager\"," +
                        "\"description\":\"desc\"," +
                        "\"tenantId\":\"tenant1\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Manager"));
    }

    @Test
    void updateRole_shouldReturnUpdatedRole() throws Exception {
        RoleUpdateRequest req = new RoleUpdateRequest();
        req.setId("role1");
        req.setName("Updated");
        req.setDescription("desc");
        Role role = Role.builder().id("role1").name("Updated").description("desc").build();
        Mockito.when(roleService.updateRole(Mockito.eq("role1"), Mockito.any(Role.class))).thenReturn(role);
        mockMvc.perform(put("/api/admin/roles/role1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\":\"role1\"," +
                        "\"name\":\"Updated\"," +
                        "\"description\":\"desc\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void deleteRole_shouldCallService() throws Exception {
        mockMvc.perform(delete("/api/admin/roles/role1"))
                .andExpect(status().isOk());
        Mockito.verify(roleService).deleteRole("role1");
    }

    @Test
    void getAllRoles_shouldReturnList() throws Exception {
        Role role = Role.builder().id("role1").name("Manager").build();
        Mockito.when(roleService.findAllRoles()).thenReturn(List.of(role));
        mockMvc.perform(get("/api/admin/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Manager"));
    }

    @Test
    void getRole_shouldReturnRole() throws Exception {
        Role role = Role.builder().id("role1").name("Manager").build();
        Mockito.when(roleService.findRoleById("role1")).thenReturn(role);
        mockMvc.perform(get("/api/admin/roles/role1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Manager"));
    }

    @Test
    void createPermission_shouldReturnPermission() throws Exception {
        Permission permission = Permission.builder().id("perm1").name("READ").build();
        Mockito.when(roleService.createPermission(Mockito.any(Permission.class))).thenReturn(permission);
        mockMvc.perform(post("/api/admin/roles/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\":\"READ\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("READ"));
    }

    @Test
    void updatePermission_shouldReturnUpdatedPermission() throws Exception {
        Permission permission = Permission.builder().id("perm1").name("WRITE").build();
        Mockito.when(roleService.updatePermission(Mockito.eq("perm1"), Mockito.any(Permission.class))).thenReturn(permission);
        mockMvc.perform(put("/api/admin/roles/permissions/perm1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\":\"WRITE\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("WRITE"));
    }

    @Test
    void deletePermission_shouldCallService() throws Exception {
        mockMvc.perform(delete("/api/admin/roles/permissions/perm1"))
                .andExpect(status().isOk());
        Mockito.verify(roleService).deletePermission("perm1");
    }

    @Test
    void getAllPermissions_shouldReturnList() throws Exception {
        Permission permission = Permission.builder().id("perm1").name("READ").build();
        Mockito.when(roleService.findAllPermissions()).thenReturn(List.of(permission));
        mockMvc.perform(get("/api/admin/roles/permissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("READ"));
    }

    @Test
    void getPermission_shouldReturnPermission() throws Exception {
        Permission permission = Permission.builder().id("perm1").name("READ").build();
        Mockito.when(roleService.findPermissionById("perm1")).thenReturn(permission);
        mockMvc.perform(get("/api/admin/roles/permissions/perm1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("READ"));
    }

    @Test
    void assignRolesToUser_shouldReturnUser() throws Exception {
        User user = User.builder().id("user1").roles(List.of("role1")).build();
        Mockito.when(userService.findById("user1")).thenReturn(java.util.Optional.of(user));
        Mockito.when(userService.updateUser(Mockito.eq("user1"), Mockito.any(User.class))).thenReturn(user);
        mockMvc.perform(put("/api/admin/roles/assign/user1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[\"role1\"]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles[0]").value("role1"));
    }

    @Test
    void cloneRole_shouldReturnClonedRole() throws Exception {
        Role source = Role.builder().id("role1").name("Manager").build();
        Role clone = Role.builder().id("role2").name("Manager Clone").build();
        Mockito.when(roleService.findRoleById("role1")).thenReturn(source);
        Mockito.when(((RoleService)Mockito.any()).createRole(Mockito.any(Role.class))).thenReturn(clone);
        mockMvc.perform(post("/api/admin/roles/clone")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"sourceRoleId\":\"role1\"," +
                        "\"newName\":\"Manager Clone\"," +
                        "\"description\":\"Cloned role\"," +
                        "\"tenantId\":\"tenant1\"" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void getAuditLogsByRole_shouldReturnLogs() throws Exception {
        RoleAuditLog log = RoleAuditLog.builder().id("log1").roleId("role1").action("UPDATE").performedBy("system").tenantId("tenant1").details("Updated role: Manager").timestamp(1710000000000L).build();
        Mockito.when(roleAuditLogRepository.findByRoleId("role1")).thenReturn(List.of(log));
        mockMvc.perform(get("/api/admin/roles/audit/role1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].action").value("UPDATE"));
    }

    @Test
    void getAuditLogsByTenant_shouldReturnLogs() throws Exception {
        RoleAuditLog log = RoleAuditLog.builder().id("log1").roleId("role1").action("UPDATE").performedBy("system").tenantId("tenant1").details("Updated role: Manager").timestamp(1710000000000L).build();
        Mockito.when(roleAuditLogRepository.findByTenantId("tenant1")).thenReturn(List.of(log));
        mockMvc.perform(get("/api/admin/roles/audit/tenant/tenant1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tenantId").value("tenant1"));
    }
} 