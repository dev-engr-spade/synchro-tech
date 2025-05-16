package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.Department;
import java.util.List;

public interface DepartmentService {
    Department createDepartment(Department department);
    Department updateDepartment(String id, Department department);
    void deleteDepartment(String id);
    Department findById(String id);
    List<Department> findByTenantId(String tenantId);
    List<Department> findByParentId(String parentId);
    Department assignManager(String departmentId, String managerUserId);
} 