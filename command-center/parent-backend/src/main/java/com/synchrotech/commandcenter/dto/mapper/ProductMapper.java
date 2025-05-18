package com.synchrotech.commandcenter.dto.mapper;

import com.synchrotech.commandcenter.model.module.Product;
import com.synchrotech.commandcenter.model.module.ProductVariant;
import com.synchrotech.commandcenter.dto.request.module.ProductRequest;
import com.synchrotech.commandcenter.dto.request.module.ProductVariantRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for Product entity to DTO.
 */
public class ProductMapper {
    public static Product toEntity(ProductRequest request) {
        if (request == null) return null;
        return Product.builder()
                .tenantId(request.getTenantId())
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .sku(request.getSku())
                .barcode(request.getBarcode())
                .price(request.getPrice())
                .cost(request.getCost())
                .attributes(request.getAttributes())
                .images(request.getImages())
                .variants(toVariantEntities(request.getVariants()))
                .active(request.isActive())
                .build();
    }

    public static List<ProductVariant> toVariantEntities(List<ProductVariantRequest> requests) {
        if (requests == null) return null;
        return requests.stream().map(ProductMapper::toVariantEntity).collect(Collectors.toList());
    }

    public static ProductVariant toVariantEntity(ProductVariantRequest request) {
        if (request == null) return null;
        return ProductVariant.builder()
                .name(request.getName())
                .sku(request.getSku())
                .barcode(request.getBarcode())
                .price(request.getPrice())
                .cost(request.getCost())
                .active(request.isActive())
                .build();
    }
} 