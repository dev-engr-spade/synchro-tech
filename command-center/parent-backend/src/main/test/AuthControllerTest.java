package com.synchrotech.commandcenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synchrotech.commandcenter.dto.request.user.LoginRequest;
import com.synchrotech.commandcenter.dto.request.user.MfaSetupRequest;
import com.synchrotech.commandcenter.dto.request.user.MfaValidateRequest;
import com.synchrotech.commandcenter.dto.request.user.MfaRecoveryRequest;
import com.synchrotech.commandcenter.dto.response.auth.AuthResponse;
import com.synchrotech.commandcenter.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_validCredentials_returnsJwt() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("user@example.com");
        request.setPassword("password123");
        AuthResponse response = new AuthResponse(true, "Login successful", "userId", "jwt.token.here");
        Mockito.when(userService.login(any())).thenReturn(response);
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.token").value("jwt.token.here"));
    }

    @Test
    void login_invalidCredentials_returnsError() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsernameOrEmail("user@example.com");
        request.setPassword("wrong");
        AuthResponse response = new AuthResponse(false, "Invalid credentials", null, null);
        Mockito.when(userService.login(any())).thenReturn(response);
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void refresh_validToken_returnsNewJwt() throws Exception {
        AuthResponse response = new AuthResponse(true, "Token refreshed", "userId", "new.jwt.token", "new.refresh.token");
        Mockito.when(userService.refreshToken(Mockito.eq("valid.refresh.token"))).thenReturn(response);
        mockMvc.perform(post("/api/auth/refresh?refreshToken=valid.refresh.token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.token").value("new.jwt.token"))
                .andExpect(jsonPath("$.refreshToken").value("new.refresh.token"));
    }

    @Test
    void refresh_invalidToken_returnsError() throws Exception {
        AuthResponse response = new AuthResponse(false, "Invalid or expired refresh token", null, null, null);
        Mockito.when(userService.refreshToken(Mockito.eq("bad.token"))).thenReturn(response);
        mockMvc.perform(post("/api/auth/refresh?refreshToken=bad.token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void mfaSetup_email_success() throws Exception {
        MfaSetupRequest request = new MfaSetupRequest();
        request.setUserId("userId");
        request.setMfaType("EMAIL");
        AuthResponse response = new AuthResponse(true, "MFA email code sent", "userId", null, null);
        Mockito.when(userService.setupMfa(any())).thenReturn(response);
        mockMvc.perform(post("/api/auth/mfa/setup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void mfaValidate_email_success() throws Exception {
        MfaValidateRequest request = new MfaValidateRequest();
        request.setUserId("userId");
        request.setCode("123456");
        AuthResponse response = new AuthResponse(true, "MFA validated", "userId", null, null);
        Mockito.when(userService.validateMfa(any())).thenReturn(response);
        mockMvc.perform(post("/api/auth/mfa/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void mfaRecovery_success() throws Exception {
        MfaRecoveryRequest request = new MfaRecoveryRequest();
        request.setUserId("userId");
        request.setRecoveryCode("recovery123");
        AuthResponse response = new AuthResponse(true, "Recovery code accepted", "userId", null, null);
        Mockito.when(userService.useMfaRecoveryCode(any())).thenReturn(response);
        mockMvc.perform(post("/api/auth/mfa/recovery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void mfaSetup_invalidType_failure() throws Exception {
        MfaSetupRequest request = new MfaSetupRequest();
        request.setUserId("userId");
        request.setMfaType("INVALID");
        AuthResponse response = new AuthResponse(false, "Invalid MFA type", null, null, null);
        Mockito.when(userService.setupMfa(any())).thenReturn(response);
        mockMvc.perform(post("/api/auth/mfa/setup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }
} 