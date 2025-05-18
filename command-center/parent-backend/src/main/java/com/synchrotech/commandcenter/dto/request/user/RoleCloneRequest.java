package com.synchrotech.commandcenter.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleCloneRequest {
    @NotBlank
    private String sourceRoleId;
    @NotBlank
    private String newName;
    private String description;
    private String tenantId;
}
