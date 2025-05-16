package com.synchrotech.commandcenter.dto.request.user;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DepartmentCreateRequest {
    @NotBlank
    private String name;
    private String parentId;
    private String managerUserId;
    private String tenantId;
    private String description;
}

@Data
public class DepartmentUpdateRequest {
    @NotBlank
    private String id;
    private String name;
    private String parentId;
    private String managerUserId;
    private String description;
    private Boolean active;
}

@Data
public class AssignDepartmentManagerRequest {
    @NotBlank
    private String departmentId;
    @NotBlank
    private String managerUserId;
} 