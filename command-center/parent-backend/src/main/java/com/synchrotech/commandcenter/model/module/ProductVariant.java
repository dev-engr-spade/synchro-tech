package com.synchrotech.commandcenter.model.module;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    private String id;
    private String name;
    private String sku;
    private String barcode;
    private BigDecimal price;
    private BigDecimal cost;
    private boolean active;
} 