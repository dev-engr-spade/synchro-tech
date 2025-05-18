package com.synchrotech.commandcenter.dto.request.module;

import java.math.BigDecimal;

public class ProductVariantRequest {
    private String name;
    private String sku;
    private String barcode;
    private BigDecimal price;
    private BigDecimal cost;
    private boolean active;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
} 