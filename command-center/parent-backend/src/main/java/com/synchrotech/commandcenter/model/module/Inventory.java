package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * Entity representing inventory.
 */
@Document(collection = "inventory")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    private String id;
    private String tenantId;
    private String productId;
    private String productName;
    private String variantId;
    private String locationId;
    private Integer quantity;
    private Integer reservedQuantity;
    private Integer reorderLevel;
    private Integer optimalStock;
    private Date createdAt;
    private Date updatedAt;

    // Getters and setters
} 