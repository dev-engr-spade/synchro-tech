package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private String tenantId;
    private String name;
    private String description;
    private String category;
    private String sku;
    private String barcode;
    private BigDecimal price;
    private BigDecimal cost;
    private Map<String, String> attributes;
    private List<String> images;
    private List<ProductVariant> variants;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;
} 