package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Customer;
import com.synchrotech.commandcenter.dto.request.module.CustomerRequest;

public class CustomerMapper {
    public static Customer toEntity(CustomerRequest request) {
        if (request == null) return null;
        return Customer.builder()
                .tenantId(request.getTenantId())
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .active(request.isActive())
                .build();
    }
} 