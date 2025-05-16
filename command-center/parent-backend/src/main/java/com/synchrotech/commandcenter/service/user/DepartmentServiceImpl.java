package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.Department;
import com.synchrotech.commandcenter.repository.user.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department createDepartment(Department department) {
        department.setCreatedAt(System.currentTimeMillis());
        department.setUpdatedAt(System.currentTimeMillis());
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(String id, Department department) {
        Department existing = departmentRepository.findById(id).orElseThrow();
        existing.setName(department.getName());
        existing.setParentId(department.getParentId());
        existing.setManagerUserId(department.getManagerUserId());
        existing.setSettings(department.getSettings());
        existing.setUpdatedAt(System.currentTimeMillis());
        return departmentRepository.save(existing);
    }

    @Override
    public void deleteDepartment(String id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department findById(String id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Department> findByTenantId(String tenantId) {
        return departmentRepository.findByTenantId(tenantId);
    }

    @Override
    public List<Department> findByParentId(String parentId) {
        return departmentRepository.findByParentId(parentId);
    }

    @Override
    public Department assignManager(String departmentId, String managerUserId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow();
        department.setManagerUserId(managerUserId);
        department.setUpdatedAt(System.currentTimeMillis());
        return departmentRepository.save(department);
    }
} 