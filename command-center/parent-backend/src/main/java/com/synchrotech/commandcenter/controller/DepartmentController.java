package com.synchrotech.commandcenter.controller;

import com.synchrotech.commandcenter.model.user.Department;
import com.synchrotech.commandcenter.dto.request.user.DepartmentCreateRequest;
import com.synchrotech.commandcenter.dto.request.user.DepartmentUpdateRequest;
import com.synchrotech.commandcenter.dto.request.user.AssignDepartmentManagerRequest;
import com.synchrotech.commandcenter.service.user.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Department management endpoints.
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public Department createDepartment(@Valid @RequestBody DepartmentCreateRequest request) {
        Department department = Department.builder()
                .name(request.getName())
                .parentId(request.getParentId())
                .managerUserId(request.getManagerUserId())
                .tenantId(request.getTenantId())
                .settings(com.synchrotech.commandcenter.model.user.DepartmentSettings.builder()
                        .description(request.getDescription())
                        .active(true)
                        .build())
                .build();
        return departmentService.createDepartment(department);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable String id, @Valid @RequestBody DepartmentUpdateRequest request) {
        Department department = Department.builder()
                .name(request.getName())
                .parentId(request.getParentId())
                .managerUserId(request.getManagerUserId())
                .settings(com.synchrotech.commandcenter.model.user.DepartmentSettings.builder()
                        .description(request.getDescription())
                        .active(request.getActive() != null ? request.getActive() : true)
                        .build())
                .build();
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable String id) {
        departmentService.deleteDepartment(id);
    }

    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable String id) {
        return departmentService.findById(id);
    }

    @GetMapping
    public List<Department> getDepartmentsByTenant(@RequestParam String tenantId) {
        return departmentService.findByTenantId(tenantId);
    }

    @GetMapping("/parent/{parentId}")
    public List<Department> getDepartmentsByParent(@PathVariable String parentId) {
        return departmentService.findByParentId(parentId);
    }

    @PostMapping("/assign-manager")
    public Department assignManager(@Valid @RequestBody AssignDepartmentManagerRequest request) {
        return departmentService.assignManager(request.getDepartmentId(), request.getManagerUserId());
    }
} 