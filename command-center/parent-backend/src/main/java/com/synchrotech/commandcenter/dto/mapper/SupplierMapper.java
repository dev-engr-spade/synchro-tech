package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Supplier;
import com.synchrotech.commandcenter.dto.request.module.SupplierRequest;

public class SupplierMapper {
    public static Supplier toEntity(SupplierRequest request) {
        if (request == null) return null;
        return Supplier.builder()
                .tenantId(request.getTenantId())
                .name(request.getName())
                .contactName(request.getContactName())
                .contactEmail(request.getContactEmail())
                .contactPhone(request.getContactPhone())
                .address(request.getAddress())
                .active(request.isActive())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 