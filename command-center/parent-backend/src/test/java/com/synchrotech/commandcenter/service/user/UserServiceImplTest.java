package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.User;
import com.synchrotech.commandcenter.repository.user.UserRepository;
import com.synchrotech.commandcenter.repository.user.RefreshTokenRepository;
import com.synchrotech.commandcenter.util.EmailService;
import com.synchrotech.commandcenter.util.JwtUtil;
import com.synchrotech.commandcenter.dto.request.user.*;
import com.synchrotech.commandcenter.dto.response.auth.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock UserRepository userRepository;
    @Mock EmailService emailService;
    @Mock JwtUtil jwtUtil;
    @Mock RefreshTokenRepository refreshTokenRepository;
    @InjectMocks UserServiceImpl userService;

    User user;

    @BeforeEach
    void setup() {
        user = User.builder().id("1").username("testuser").email("test@example.com").password(new BCryptPasswordEncoder().encode("password123")).tenantId("tenant1").active(true).emailVerified(true).build();
    }

    @Test
    void createUser_shouldSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User created = userService.createUser(user);
        assertEquals("testuser", created.getUsername());
    }

    @Test
    void updateUser_shouldUpdateFields() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        User updated = userService.updateUser("1", user);
        assertEquals("testuser", updated.getUsername());
    }

    @Test
    void findById_shouldReturnUser() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        Optional<User> found = userService.findById("1");
        assertTrue(found.isPresent());
    }

    @Test
    void findByTenantId_shouldReturnList() {
        when(userRepository.findByTenantId("tenant1")).thenReturn(List.of(user));
        List<User> users = userService.findByTenantId("tenant1");
        assertEquals(1, users.size());
    }

    @Test
    void deleteUser_shouldDelete() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById("1");
        userService.deleteUser("1");
        verify(userRepository).deleteById("1");
    }

    @Test
    void registerUser_shouldReturnSuccess() {
        UserRegistrationRequest req = new UserRegistrationRequest();
        req.setUsername("testuser"); req.setEmail("test@example.com"); req.setPassword("password123"); req.setTenantId("tenant1");
        when(userRepository.findByTenantId("tenant1")).thenReturn(List.of());
        when(userRepository.save(any(User.class))).thenReturn(user);
        AuthResponse resp = userService.registerUser(req);
        assertTrue(resp.isSuccess());
    }

    @Test
    void registerUser_duplicateEmail_shouldFail() {
        UserRegistrationRequest req = new UserRegistrationRequest();
        req.setUsername("testuser"); req.setEmail("test@example.com"); req.setPassword("password123"); req.setTenantId("tenant1");
        when(userRepository.findByTenantId("tenant1")).thenReturn(List.of(user));
        AuthResponse resp = userService.registerUser(req);
        assertFalse(resp.isSuccess());
    }

    @Test
    void login_shouldReturnSuccess() {
        LoginRequest req = new LoginRequest();
        req.setUsernameOrEmail("testuser"); req.setPassword("password123");
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(jwtUtil.generateToken(any(), any(), any(), any())).thenReturn("jwt");
        AuthResponse resp = userService.login(req);
        assertTrue(resp.isSuccess());
    }

    @Test
    void login_wrongPassword_shouldFail() {
        LoginRequest req = new LoginRequest();
        req.setUsernameOrEmail("testuser"); req.setPassword("wrong");
        when(userRepository.findAll()).thenReturn(List.of(user));
        AuthResponse resp = userService.login(req);
        assertFalse(resp.isSuccess());
    }

    @Test
    void requestPasswordReset_shouldSendEmail() {
        PasswordResetRequest req = new PasswordResetRequest();
        req.setEmail("test@example.com");
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        AuthResponse resp = userService.requestPasswordReset(req);
        assertTrue(resp.isSuccess());
    }

    @Test
    void confirmPasswordReset_shouldResetPassword() {
        PasswordResetConfirmRequest req = new PasswordResetConfirmRequest();
        req.setToken("token"); req.setNewPassword("newpassword123");
        user.setResetToken("token"); user.setResetTokenExpiry(System.currentTimeMillis() + 10000);
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        AuthResponse resp = userService.confirmPasswordReset(req);
        assertTrue(resp.isSuccess());
    }

    @Test
    void setupMfa_email_shouldSendCode() {
        MfaSetupRequest req = new MfaSetupRequest();
        req.setUserId("1"); req.setMfaType("EMAIL");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        AuthResponse resp = userService.setupMfa(req);
        assertTrue(resp.isSuccess());
    }

    @Test
    void setupMfa_totp_shouldReturnSecret() {
        MfaSetupRequest req = new MfaSetupRequest();
        req.setUserId("1"); req.setMfaType("TOTP");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        AuthResponse resp = userService.setupMfa(req);
        assertTrue(resp.isSuccess());
    }

    @Test
    void validateMfa_email_shouldSucceed() {
        MfaValidateRequest req = new MfaValidateRequest();
        req.setUserId("1"); req.setCode("123456");
        user.setMfaEnabled(true); user.setMfaType("EMAIL"); user.setMfaEmailCode("123456"); user.setMfaEmailCodeExpiry(System.currentTimeMillis() + 10000);
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        AuthResponse resp = userService.validateMfa(req);
        assertTrue(resp.isSuccess());
    }

    @Test
    void validateMfa_totp_shouldFail() {
        MfaValidateRequest req = new MfaValidateRequest();
        req.setUserId("1"); req.setCode("wrong");
        user.setMfaEnabled(true); user.setMfaType("TOTP"); user.setMfaSecret("secret");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        AuthResponse resp = userService.validateMfa(req);
        assertFalse(resp.isSuccess());
    }
} 