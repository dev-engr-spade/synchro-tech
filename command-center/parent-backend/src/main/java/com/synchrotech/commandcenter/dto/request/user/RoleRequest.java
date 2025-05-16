package com.synchrotech.commandcenter.dto.request.user;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleCreateRequest {
    @NotBlank
    private String name;
    private String description;
    private List<String> permissionIds;
    private String tenantId;
}

@Data
public class RoleUpdateRequest {
    @NotBlank
    private String id;
    private String name;
    private String description;
    private List<String> permissionIds;
}

@Data
public class RoleCloneRequest {
    @NotBlank
    private String sourceRoleId;
    @NotBlank
    private String newName;
    private String description;
    private String tenantId;
} 