package com.synchrotech.commandcenter.dto.request.user;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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

@Getter
@Setter
public class PasswordResetRequest {
    @NotBlank
    @Email
    private String email;
}

@Getter
@Setter
public class PasswordResetConfirmRequest {
    @NotBlank
    private String token;
    @NotBlank
    @Size(min = 8, max = 100)
    private String newPassword;
}

@Getter
@Setter
public class MfaSetupRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String mfaType; // "EMAIL" or "TOTP"
}

@Getter
@Setter
public class MfaValidateRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String code;
}

@Getter
@Setter
public class MfaRecoveryRequest {
    @NotBlank
    private String userId;
    @NotBlank
    private String recoveryCode;
} 