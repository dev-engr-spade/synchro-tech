package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.CustomerGroup;
import com.synchrotech.commandcenter.dto.request.module.CustomerGroupRequest;

public class CustomerGroupMapper {
    public static CustomerGroup toEntity(CustomerGroupRequest request) {
        if (request == null) return null;
        return CustomerGroup.builder()
                .tenantId(request.getTenantId())
                .name(request.getName())
                .description(request.getDescription())
                .customerIds(request.getCustomerIds())
                .active(request.isActive())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 