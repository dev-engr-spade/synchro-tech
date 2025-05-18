package com.synchrotech.commandcenter.dto.request.module;

import java.util.Date;

/**
 * DTO for inventory module requests.
 */
public class InventoryRequest {
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

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getVariantId() { return variantId; }
    public void setVariantId(String variantId) { this.variantId = variantId; }
    public String getLocationId() { return locationId; }
    public void setLocationId(String locationId) { this.locationId = locationId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Integer getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(Integer reservedQuantity) { this.reservedQuantity = reservedQuantity; }
    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }
    public Integer getOptimalStock() { return optimalStock; }
    public void setOptimalStock(Integer optimalStock) { this.optimalStock = optimalStock; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
} 