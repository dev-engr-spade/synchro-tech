package com.synchrotech.commandcenter.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserUpdateRequest {
    @NotBlank
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String profilePictureUrl;
    private String departmentId;
    private List<String> roleIds;
    private boolean active;
}
