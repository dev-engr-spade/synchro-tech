package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.user.User;
import com.synchrotech.commandcenter.dto.request.user.UserRequest;
import com.synchrotech.commandcenter.dto.response.user.UserResponse;
import com.synchrotech.commandcenter.model.user.UserStatus;

public class UserMapper {
    public static User toEntity(UserRequest request) {
        if (request == null) return null;
        User user = new User();
        user.setTenantId(request.getTenantId());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPasswordHash());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDepartmentId(request.getDepartmentId());
        user.setRoles(request.getRoles());
        user.setProfile(request.getProfile());
        user.setPreferences(request.getPreferences());
        if (request.getStatus() != null) user.setStatus(UserStatus.valueOf(request.getStatus()));
        user.setLastLogin(request.getLastLogin());
        user.setCreatedAt(request.getCreatedAt());
        user.setUpdatedAt(request.getUpdatedAt());
        return user;
    }
    public static UserResponse toResponse(User user) {
        if (user == null) return null;
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setTenantId(user.getTenantId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setDepartmentId(user.getDepartmentId());
        response.setRoles(user.getRoles());
        response.setProfile(user.getProfile());
        response.setPreferences(user.getPreferences());
        if (user.getStatus() != null) response.setStatus(user.getStatus().name());
        response.setLastLogin(user.getLastLogin());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}