package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Inventory;
import com.synchrotech.commandcenter.dto.request.module.InventoryRequest;

/**
 * Mapper for Inventory entity to DTO.
 */
public class InventoryMapper {
    public static Inventory toEntity(InventoryRequest request) {
        if (request == null) return null;
        return Inventory.builder()
                .tenantId(request.getTenantId())
                .productId(request.getProductId())
                .productName(request.getProductName())
                .variantId(request.getVariantId())
                .locationId(request.getLocationId())
                .quantity(request.getQuantity())
                .reservedQuantity(request.getReservedQuantity())
                .reorderLevel(request.getReorderLevel())
                .optimalStock(request.getOptimalStock())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
} 