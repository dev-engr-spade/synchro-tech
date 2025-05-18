package com.synchrotech.commandcenter.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RoleUpdateRequest {
    @NotBlank
    private String id;
    private String name;
    private String description;
    private List<String> permissionIds;
}
