package com.synchrotech.commandcenter.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
