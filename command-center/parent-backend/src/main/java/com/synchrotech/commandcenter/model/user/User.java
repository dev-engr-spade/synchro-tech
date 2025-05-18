package com.synchrotech.commandcenter.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private String passwordHash;
    private List<String> roles;
    private String tenantId;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;
    private boolean emailVerified;
    private String verificationToken;
    private Long verificationTokenExpiry;
    private String resetToken;
    private Long resetTokenExpiry;
    private String firstName;
    private String lastName;
    private String phone;
    private String profilePictureUrl;
    private String departmentId;
    private Map<String, Object> profile;
    private Map<String, Object> preferences;
    private UserStatus status;
    private Date lastLogin;
    private List<String> roleAssignmentAudit;
} 