package com.synchrotech.commandcenter.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BulkUserCreateRequest {
    @NotBlank
    private String tenantId;
    @NotNull
    @Size(min = 1)
    private List<UserCreateRequest> users;
}
