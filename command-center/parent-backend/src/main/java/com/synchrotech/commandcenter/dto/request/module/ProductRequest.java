package com.synchrotech.commandcenter.dto.request.module;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductRequest {
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
    private List<ProductVariantRequest> variants;
    private boolean active;

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
    public Map<String, String> getAttributes() { return attributes; }
    public void setAttributes(Map<String, String> attributes) { this.attributes = attributes; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
    public List<ProductVariantRequest> getVariants() { return variants; }
    public void setVariants(List<ProductVariantRequest> variants) { this.variants = variants; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
} 