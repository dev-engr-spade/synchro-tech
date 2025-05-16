package com.synchrotech.commandcenter.service.tenant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Slf4j
@Component
public class TenantUsageAlertScheduler {
    private final TenantService tenantService;

    @Autowired
    public TenantUsageAlertScheduler(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    // Run every 5 minutes
    @Scheduled(fixedRate = 300_000)
    public void checkTenantUsage() {
        List<com.synchrotech.commandcenter.model.tenant.Tenant> tenants = tenantService.findAll();
        for (var tenant : tenants) {
            var metrics = tenantService.getTenantMetrics(tenant.getId());
            long apiCalls = (long) metrics.getOrDefault("apiCallVolume", 0L);
            long userStorage = (long) metrics.getOrDefault("userStorageBytes", 0L);
            long inventoryStorage = (long) metrics.getOrDefault("inventoryStorageBytes", 0L);
            if (apiCalls > 10_000) {
                log.warn("[ALERT] Tenant {} exceeded API call threshold: {}", tenant.getSubdomain(), apiCalls);
            }
            if (userStorage + inventoryStorage > 100_000_000) { // 100MB
                log.warn("[ALERT] Tenant {} exceeded storage threshold: {} bytes", tenant.getSubdomain(), userStorage + inventoryStorage);
            }
        }
    }
} 