package com.synchrotech.commandcenter.controller;

import com.synchrotech.commandcenter.model.user.Department;
import com.synchrotech.commandcenter.model.user.DepartmentSettings;
import com.synchrotech.commandcenter.dto.request.user.DepartmentCreateRequest;
import com.synchrotech.commandcenter.dto.request.user.DepartmentUpdateRequest;
import com.synchrotech.commandcenter.dto.request.user.AssignDepartmentManagerRequest;
import com.synchrotech.commandcenter.service.user.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DepartmentService departmentService;

    @Test
    void createDepartment_shouldReturnDepartment() throws Exception {
        DepartmentCreateRequest req = new DepartmentCreateRequest();
        req.setName("Engineering");
        req.setTenantId("tenant1");
        req.setDescription("Engineering department");
        Department department = Department.builder().id("dep1").name("Engineering").tenantId("tenant1").settings(DepartmentSettings.builder().description("Engineering department").active(true).build()).build();
        Mockito.when(departmentService.createDepartment(Mockito.any(Department.class))).thenReturn(department);
        mockMvc.perform(post("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\":\"Engineering\"," +
                        "\"tenantId\":\"tenant1\"," +
                        "\"description\":\"Engineering department\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"));
    }

    @Test
    void updateDepartment_shouldReturnUpdatedDepartment() throws Exception {
        DepartmentUpdateRequest req = new DepartmentUpdateRequest();
        req.setId("dep1");
        req.setName("Updated");
        req.setDescription("Updated desc");
        req.setActive(false);
        Department department = Department.builder().id("dep1").name("Updated").settings(DepartmentSettings.builder().description("Updated desc").active(false).build()).build();
        Mockito.when(departmentService.updateDepartment(Mockito.eq("dep1"), Mockito.any(Department.class))).thenReturn(department);
        mockMvc.perform(put("/api/departments/dep1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"id\":\"dep1\"," +
                        "\"name\":\"Updated\"," +
                        "\"description\":\"Updated desc\"," +
                        "\"active\":false" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void getDepartment_shouldReturnDepartment() throws Exception {
        Department department = Department.builder().id("dep1").name("Engineering").build();
        Mockito.when(departmentService.findById("dep1")).thenReturn(department);
        mockMvc.perform(get("/api/departments/dep1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"));
    }

    @Test
    void getDepartmentsByTenant_shouldReturnList() throws Exception {
        Department department = Department.builder().id("dep1").tenantId("tenant1").build();
        Mockito.when(departmentService.findByTenantId("tenant1")).thenReturn(List.of(department));
        mockMvc.perform(get("/api/departments?tenantId=tenant1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tenantId").value("tenant1"));
    }

    @Test
    void getDepartmentsByParent_shouldReturnList() throws Exception {
        Department department = Department.builder().id("dep2").parentId("dep1").build();
        Mockito.when(departmentService.findByParentId("dep1")).thenReturn(List.of(department));
        mockMvc.perform(get("/api/departments/parent/dep1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].parentId").value("dep1"));
    }

    @Test
    void assignManager_shouldReturnDepartment() throws Exception {
        AssignDepartmentManagerRequest req = new AssignDepartmentManagerRequest();
        req.setDepartmentId("dep1");
        req.setManagerUserId("user1");
        Department department = Department.builder().id("dep1").managerUserId("user1").build();
        Mockito.when(departmentService.assignManager("dep1", "user1")).thenReturn(department);
        mockMvc.perform(post("/api/departments/assign-manager")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"departmentId\":\"dep1\"," +
                        "\"managerUserId\":\"user1\"" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.managerUserId").value("user1"));
    }

    @Test
    void deleteDepartment_shouldCallService() throws Exception {
        mockMvc.perform(delete("/api/departments/dep1"))
                .andExpect(status().isOk());
        Mockito.verify(departmentService).deleteDepartment("dep1");
    }
} 