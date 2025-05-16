package com.synchrotech.commandcenter.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;
    private List<String> roles;
    private String tenantId;
    private boolean active;
    private Long createdAt;
    private Long updatedAt;
    private boolean emailVerified;
    private String verificationToken;
    private Long verificationTokenExpiry;
    private String resetToken;
    private Long resetTokenExpiry;
    private boolean mfaEnabled;
    private String mfaType; // "EMAIL" or "TOTP"
    private String mfaSecret; // for TOTP
    private List<String> mfaRecoveryCodes;
    private String mfaEmailCode;
    private Long mfaEmailCodeExpiry;
} 