package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.Department;
import com.synchrotech.commandcenter.model.user.DepartmentSettings;
import com.synchrotech.commandcenter.repository.user.DepartmentRepository;
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
public class DepartmentServiceImplTest {
    @Mock DepartmentRepository departmentRepository;
    @InjectMocks DepartmentServiceImpl departmentService;
    Department department;

    @BeforeEach
    void setup() {
        department = Department.builder().id("dep1").name("Engineering").tenantId("tenant1").settings(DepartmentSettings.builder().description("desc").active(true).build()).build();
    }

    @Test
    void createDepartment_shouldSave() {
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        Department created = departmentService.createDepartment(department);
        assertEquals("Engineering", created.getName());
    }

    @Test
    void updateDepartment_shouldUpdate() {
        when(departmentRepository.findById("dep1")).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        Department updated = departmentService.updateDepartment("dep1", department);
        assertEquals("Engineering", updated.getName());
    }

    @Test
    void deleteDepartment_shouldDelete() {
        doNothing().when(departmentRepository).deleteById("dep1");
        departmentService.deleteDepartment("dep1");
        verify(departmentRepository).deleteById("dep1");
    }

    @Test
    void findById_shouldReturnDepartment() {
        when(departmentRepository.findById("dep1")).thenReturn(Optional.of(department));
        Department found = departmentService.findById("dep1");
        assertNotNull(found);
    }

    @Test
    void findByTenantId_shouldReturnList() {
        when(departmentRepository.findByTenantId("tenant1")).thenReturn(List.of(department));
        List<Department> list = departmentService.findByTenantId("tenant1");
        assertEquals(1, list.size());
    }

    @Test
    void findByParentId_shouldReturnList() {
        when(departmentRepository.findByParentId("parent1")).thenReturn(List.of(department));
        List<Department> list = departmentService.findByParentId("parent1");
        assertEquals(1, list.size());
    }

    @Test
    void assignManager_shouldUpdateManager() {
        when(departmentRepository.findById("dep1")).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        Department updated = departmentService.assignManager("dep1", "user1");
        assertEquals("user1", updated.getManagerUserId());
    }
} 