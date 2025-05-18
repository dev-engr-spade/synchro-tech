package com.synchrotech.commandcenter.dto.request.user;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for user requests.
 */
@Data
public class UserRegistrationRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    private String tenantId;
}

