package com.synchrotech.commandcenter.service.user;

import com.synchrotech.commandcenter.model.user.User;
import com.synchrotech.commandcenter.repository.user.UserRepository;
import com.synchrotech.commandcenter.security.TenantContextHolder;
import com.synchrotech.commandcenter.dto.request.user.UserRegistrationRequest;
import com.synchrotech.commandcenter.dto.response.auth.AuthResponse;
import com.synchrotech.commandcenter.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import com.synchrotech.commandcenter.dto.request.user.LoginRequest;
import com.synchrotech.commandcenter.util.JwtUtil;
import java.util.HashMap;
import com.synchrotech.commandcenter.model.user.RefreshToken;
import com.synchrotech.commandcenter.repository.user.RefreshTokenRepository;
import com.synchrotech.commandcenter.dto.request.user.PasswordResetRequest;
import com.synchrotech.commandcenter.dto.request.user.PasswordResetConfirmRequest;
import com.synchrotech.commandcenter.exception.ResourceNotFoundException;
import com.synchrotech.commandcenter.exception.UnauthorizedException;

/**
 * Implementation of UserService.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final EmailService emailService;
    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;
    private final JwtUtil jwtUtil;
    // Track failed login attempts in memory (for demo; use Redis for prod)
    private final java.util.concurrent.ConcurrentHashMap<String, Integer> failedAttempts = new java.util.concurrent.ConcurrentHashMap<>();
    private final int MAX_FAILED_ATTEMPTS = 5;
    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenExpiryMs = 1000L * 60 * 60 * 24 * 7; // 7 days

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailService emailService, JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public User createUser(User user) {
        user.setTenantId(TenantContextHolder.getTenant());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String id, User user) {
        Optional<User> existingOpt = userRepository.findById(id);
        if (existingOpt.isEmpty()) throw new ResourceNotFoundException("User not found");
        User existing = existingOpt.get();
        if (!TenantContextHolder.getTenant().equals(existing.getTenantId())) {
            throw new UnauthorizedException("Unauthorized tenant access");
        }
        // Update fields as needed
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        existing.setPasswordHash(user.getPasswordHash());
        existing.setRoles(user.getRoles());
        existing.setActive(user.isActive());
        existing.setUpdatedAt(new java.util.Date());
        return userRepository.save(existing);
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.filter(u -> TenantContextHolder.getTenant().equals(u.getTenantId()));
    }

    @Override
    public List<User> findByTenantId(String tenantId) {
        return userRepository.findByTenantId(tenantId);
    }

    @Override
    public void deleteUser(String id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent() && TenantContextHolder.getTenant().equals(userOpt.get().getTenantId())) {
            userRepository.deleteById(id);
        } else {
            throw new UnauthorizedException("Unauthorized tenant access");
        }
    }

    @Override
    public AuthResponse registerUser(UserRegistrationRequest request) {
        // Validate uniqueness
        if (userRepository.findByTenantId(request.getTenantId()).stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(request.getEmail()))) {
            return new AuthResponse(false, "Email already registered", null, null, null);
        }
        if (userRepository.findByTenantId(request.getTenantId()).stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(request.getUsername()))) {
            return new AuthResponse(false, "Username already taken", null, null, null);
        }
        // Password strength (basic)
        if (request.getPassword().length() < 8) {
            return new AuthResponse(false, "Password too short", null, null, null);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String token = UUID.randomUUID().toString();
        long expiry = System.currentTimeMillis() + 1000 * 60 * 60 * 24; // 24h
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(encodedPassword)
                .tenantId(request.getTenantId())
                .active(false)
                .emailVerified(false)
                .verificationToken(token)
                .verificationTokenExpiry(expiry)
                .createdAt(new java.util.Date())
                .updatedAt(new java.util.Date())
                .build();
        userRepository.save(user);
        // Send verification email
        String verificationLink = frontendUrl + "/verify?token=" + token;
        emailService.sendVerificationEmail(user.getEmail(), user.getUsername(), verificationLink);
        return new AuthResponse(true, "Registration successful. Please verify your email.", user.getId(), null, null);
    }

    @Override
    public AuthResponse verifyEmail(String token) {
        if (!StringUtils.hasText(token)) return new AuthResponse(false, "Invalid token", null, null, null);
        Optional<User> userOpt = userRepository.findAll().stream().filter(u -> token.equals(u.getVerificationToken())).findFirst();
        if (userOpt.isEmpty()) return new AuthResponse(false, "Invalid or expired token", null, null, null);
        User user = userOpt.get();
        if (user.getVerificationTokenExpiry() == null || user.getVerificationTokenExpiry() < System.currentTimeMillis()) {
            return new AuthResponse(false, "Token expired", null, null, null);
        }
        user.setActive(true);
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiry(null);
        userRepository.save(user);
        return new AuthResponse(true, "Email verified. Account activated.", user.getId(), null, null);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        String input = request.getUsernameOrEmail();
        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(input) || u.getUsername().equalsIgnoreCase(input))
                .findFirst().orElse(null);
        if (user == null) {
            return new AuthResponse(false, "Invalid credentials", null, null, null);
        }
        if (!user.isActive() || !user.isEmailVerified()) {
            return new AuthResponse(false, "Account not activated or email not verified", null, null, null);
        }
        // Check lockout
        if (failedAttempts.getOrDefault(user.getId(), 0) >= MAX_FAILED_ATTEMPTS) {
            return new AuthResponse(false, "Account locked due to too many failed attempts", null, null, null);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            failedAttempts.merge(user.getId(), 1, Integer::sum);
            return new AuthResponse(false, "Invalid credentials", null, null, null);
        }
        // Reset failed attempts on success
        failedAttempts.remove(user.getId());
        // Generate JWT
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getTenantId(), claims);
        // Issue refresh token
        String refreshTokenStr = java.util.UUID.randomUUID().toString();
        long expiry = System.currentTimeMillis() + refreshTokenExpiryMs;
        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.save(RefreshToken.builder().userId(user.getId()).token(refreshTokenStr).expiry(expiry).build());
        return new AuthResponse(true, "Login successful", user.getId(), token, refreshTokenStr);
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        var tokenOpt = refreshTokenRepository.findByToken(refreshToken);
        if (tokenOpt.isEmpty() || tokenOpt.get().getExpiry() < System.currentTimeMillis()) {
            return new AuthResponse(false, "Invalid or expired refresh token", null, null, null);
        }
        var token = tokenOpt.get();
        var userOpt = userRepository.findById(token.getUserId());
        if (userOpt.isEmpty()) {
            return new AuthResponse(false, "User not found", null, null, null);
        }
        var user = userOpt.get();
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        String newJwt = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getTenantId(), claims);
        // Rotate refresh token
        String newRefresh = java.util.UUID.randomUUID().toString();
        long expiry = System.currentTimeMillis() + refreshTokenExpiryMs;
        refreshTokenRepository.deleteByUserId(user.getId());
        refreshTokenRepository.save(RefreshToken.builder().userId(user.getId()).token(newRefresh).expiry(expiry).build());
        return new AuthResponse(true, "Token refreshed", user.getId(), newJwt, newRefresh);
    }

    @Override
    public void logout(String userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Override
    public AuthResponse requestPasswordReset(PasswordResetRequest request) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(request.getEmail()))
                .findFirst().orElse(null);
        if (user == null) {
            return new AuthResponse(true, "If the email exists, a reset link has been sent.", null, null, null);
        }
        String token = java.util.UUID.randomUUID().toString();
        long expiry = System.currentTimeMillis() + 1000 * 60 * 30; // 30 min
        user.setResetToken(token);
        user.setResetTokenExpiry(expiry);
        userRepository.save(user);
        String resetLink = frontendUrl + "/reset-password?token=" + token;
        emailService.sendVerificationEmail(user.getEmail(), user.getUsername(), "Reset your password: " + resetLink);
        return new AuthResponse(true, "If the email exists, a reset link has been sent.", null, null, null);
    }

    @Override
    public AuthResponse confirmPasswordReset(PasswordResetConfirmRequest request) {
        User user = userRepository.findAll().stream()
                .filter(u -> request.getToken().equals(u.getResetToken()))
                .findFirst().orElse(null);
        if (user == null || user.getResetTokenExpiry() == null || user.getResetTokenExpiry() < System.currentTimeMillis()) {
            return new AuthResponse(false, "Invalid or expired reset token", null, null, null);
        }
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
        return new AuthResponse(true, "Password reset successful", user.getId(), null, null);
    }
} 