package com.synchrotech.commandcenter.controller;

import com.synchrotech.commandcenter.dto.request.user.UserRegistrationRequest;
import com.synchrotech.commandcenter.dto.request.user.LoginRequest;
import com.synchrotech.commandcenter.dto.request.user.PasswordResetRequest;
import com.synchrotech.commandcenter.dto.request.user.PasswordResetConfirmRequest;
import com.synchrotech.commandcenter.dto.response.auth.AuthResponse;
import com.synchrotech.commandcenter.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * Authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }

    @GetMapping("/verify")
    public AuthResponse verifyEmail(@RequestParam("token") String token) {
        return userService.verifyEmail(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestParam("refreshToken") String refreshToken) {
        return userService.refreshToken(refreshToken);
    }

    @PostMapping("/logout")
    public AuthResponse logout(@RequestParam("userId") String userId) {
        userService.logout(userId);
        return new AuthResponse(true, "Logged out", userId, null, null);
    }

    @PostMapping("/request-reset")
    public AuthResponse requestReset(@Valid @RequestBody PasswordResetRequest request) {
        return userService.requestPasswordReset(request);
    }

    @PostMapping("/reset-password")
    public AuthResponse resetPassword(@Valid @RequestBody PasswordResetConfirmRequest request) {
        return userService.confirmPasswordReset(request);
    }

} 