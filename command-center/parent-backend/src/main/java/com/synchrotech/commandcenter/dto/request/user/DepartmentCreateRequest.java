package com.synchrotech.commandcenter.dto.request.user;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class DepartmentCreateRequest {
    @NotBlank
    private String name;
    private String parentId;
    private String managerUserId;
    private String tenantId;
    private String description;
}

