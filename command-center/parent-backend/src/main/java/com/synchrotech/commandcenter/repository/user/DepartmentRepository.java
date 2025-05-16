package com.synchrotech.commandcenter.repository.user;

import com.synchrotech.commandcenter.model.user.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface DepartmentRepository extends MongoRepository<Department, String> {
    List<Department> findByTenantId(String tenantId);
    List<Department> findByParentId(String parentId);
} 