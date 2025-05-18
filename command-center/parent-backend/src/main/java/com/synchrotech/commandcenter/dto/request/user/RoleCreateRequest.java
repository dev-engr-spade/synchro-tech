package com.synchrotech.commandcenter.dto.request.user;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RoleCreateRequest {
    @NotBlank
    private String name;
    private String description;
    private List<String> permissionIds;
    private String tenantId;
}
