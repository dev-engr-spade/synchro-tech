package com.synchrotech.commandcenter.controller;

import com.synchrotech.commandcenter.model.user.User;
import com.synchrotech.commandcenter.dto.request.user.UserCreateRequest;
import com.synchrotech.commandcenter.dto.request.user.UserUpdateRequest;
import com.synchrotech.commandcenter.dto.request.user.BulkUserCreateRequest;
import com.synchrotech.commandcenter.service.user.UserService;
import com.synchrotech.commandcenter.service.user.ProfilePictureStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import java.util.List;
import java.io.File;
import java.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * User management endpoints.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ProfilePictureStorageService profilePictureStorageService;

    @Autowired
    public UserController(UserService userService, ProfilePictureStorageService profilePictureStorageService) {
        this.userService = userService;
        this.profilePictureStorageService = profilePictureStorageService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody UserCreateRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .profilePictureUrl(request.getProfilePictureUrl())
                .departmentId(request.getDepartmentId())
                .roles(request.getRoleIds())
                .tenantId(request.getTenantId())
                .active(request.isActive())
                .createdAt(new java.util.Date())
                .updatedAt(new java.util.Date())
                .build();
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @Valid @RequestBody UserUpdateRequest request) {
        User user = User.builder()
                .id(id)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .profilePictureUrl(request.getProfilePictureUrl())
                .departmentId(request.getDepartmentId())
                .roles(request.getRoleIds())
                .active(request.isActive())
                .updatedAt(new java.util.Date())
                .build();
        return userService.updateUser(id, user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.findById(id).orElse(null);
    }

    @GetMapping
    public List<User> getUsersByTenant(@RequestParam String tenantId) {
        return userService.findByTenantId(tenantId);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @PostMapping("/bulk")
    public List<User> bulkCreateUsers(@Valid @RequestBody BulkUserCreateRequest request) {
        return request.getUsers().stream().map(u -> {
            User user = User.builder()
                    .username(u.getUsername())
                    .email(u.getEmail())
                    .firstName(u.getFirstName())
                    .lastName(u.getLastName())
                    .phone(u.getPhone())
                    .profilePictureUrl(u.getProfilePictureUrl())
                    .departmentId(u.getDepartmentId())
                    .roles(u.getRoleIds())
                    .tenantId(request.getTenantId())
                    .active(u.isActive())
                    .createdAt(new java.util.Date())
                    .updatedAt(new java.util.Date())
                    .build();
            return userService.createUser(user);
        }).toList();
    }

    // Profile management endpoints
    @PutMapping("/{id}/profile")
    public User updateProfile(@PathVariable String id, @RequestBody UserUpdateRequest request) {
        // Only allow updating profile fields
        User user = User.builder()
                .id(id)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .profilePictureUrl(request.getProfilePictureUrl())
                .build();
        return userService.updateUser(id, user);
    }

    @PutMapping("/{id}/status")
    public User updateStatus(@PathVariable String id, @RequestParam boolean active) {
        User user = userService.findById(id).orElse(null);
        if (user != null) {
            user.setActive(active);
            user.setUpdatedAt(new java.util.Date());
            return userService.updateUser(id, user);
        }
        return null;
    }

    @Operation(summary = "Upload user profile picture", description = "Upload a profile picture for the user and update the profilePictureUrl field")
    @ApiResponse(responseCode = "200", description = "Profile picture uploaded successfully, returns the URL")
    @PostMapping("/{id}/profile-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @Parameter(description = "User ID") @PathVariable String id,
            @Parameter(description = "Profile picture file") @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }
        try {
            User user = userService.findById(id).orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            String tenantId = user.getTenantId();
            String url = profilePictureStorageService.store(id, tenantId, file);
            user.setProfilePictureUrl(url);
            userService.updateUser(id, user);
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }
} 