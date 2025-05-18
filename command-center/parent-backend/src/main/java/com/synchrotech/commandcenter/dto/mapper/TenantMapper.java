package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.tenant.Tenant;
import com.synchrotech.commandcenter.dto.request.tenant.TenantRequest;
import java.util.Date;
import java.util.Map;

/**
 * Mapper for Tenant entity to DTO.
 */
public class TenantMapper {
    public static Tenant toEntity(TenantRequest request) {
        if (request == null) return null;
        Map<String, Object> settings = request.getSettings() instanceof Map ? (Map<String, Object>) request.getSettings() : null;
        return Tenant.builder()
                .name(request.getName())
                .subdomain(request.getSubdomain())
                .industry(request.getIndustry())
                .settings(settings)
                .active(true)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
    }

    // Add more mapping methods as needed
} 