package com.synchrotech.commandcenter.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignDepartmentManagerRequest {
    @NotBlank
    private String departmentId;
    @NotBlank
    private String managerUserId;
}
