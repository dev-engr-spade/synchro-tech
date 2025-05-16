package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.User;
import java.util.List;
import java.util.Optional;
import com.synchrotech.commandcenter.dto.request.user.UserRegistrationRequest;
import com.synchrotech.commandcenter.dto.response.auth.AuthResponse;
import com.synchrotech.commandcenter.dto.request.user.LoginRequest;
import com.synchrotech.commandcenter.dto.request.user.PasswordResetRequest;
import com.synchrotech.commandcenter.dto.request.user.PasswordResetConfirmRequest;
import com.synchrotech.commandcenter.dto.request.user.MfaSetupRequest;
import com.synchrotech.commandcenter.dto.request.user.MfaValidateRequest;
import com.synchrotech.commandcenter.dto.request.user.MfaRecoveryRequest;

/**
 * Service interface for user operations.
 */
public interface UserService {
    User createUser(User user);
    User updateUser(String id, User user);
    Optional<User> findById(String id);
    List<User> findByTenantId(String tenantId);
    void deleteUser(String id);
    AuthResponse registerUser(UserRegistrationRequest request);
    AuthResponse verifyEmail(String token);
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
    void logout(String userId);
    AuthResponse requestPasswordReset(PasswordResetRequest request);
    AuthResponse confirmPasswordReset(PasswordResetConfirmRequest request);
    AuthResponse setupMfa(MfaSetupRequest request);
    AuthResponse validateMfa(MfaValidateRequest request);
    AuthResponse useMfaRecoveryCode(MfaRecoveryRequest request);
} 