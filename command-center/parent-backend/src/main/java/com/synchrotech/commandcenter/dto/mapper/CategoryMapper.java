package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Category;
import com.synchrotech.commandcenter.dto.request.module.CategoryRequest;

public class CategoryMapper {
    public static Category toEntity(CategoryRequest request) {
        if (request == null) return null;
        return Category.builder()
                .tenantId(request.getTenantId())
                .name(request.getName())
                .description(request.getDescription())
                .active(request.isActive())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 