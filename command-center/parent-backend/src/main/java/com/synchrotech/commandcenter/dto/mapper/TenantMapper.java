package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.tenant.Tenant;
import com.synchrotech.commandcenter.dto.request.tenant.TenantRequest;

/**
 * Mapper for Tenant entity to DTO.
 */
public class TenantMapper {
    public static Tenant toEntity(TenantRequest request) {
        if (request == null) return null;
        return Tenant.builder()
                .name(request.getName())
                .subdomain(request.getSubdomain())
                .industry(request.getIndustry())
                .settings(request.getSettings())
                .branding(request.getBranding())
                .enabledFeatures(request.getEnabledFeatures())
                .active(true)
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();
    }

    // Add more mapping methods as needed
} 