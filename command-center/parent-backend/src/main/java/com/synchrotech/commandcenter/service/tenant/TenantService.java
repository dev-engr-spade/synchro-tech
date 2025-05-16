package com.synchrotech.commandcenter.service.tenant;

import com.synchrotech.commandcenter.model.tenant.Tenant;
import com.synchrotech.commandcenter.dto.request.tenant.TenantRequest;
import java.util.List;
import java.util.Optional;
import com.synchrotech.commandcenter.model.tenant.TenantSettings;
import java.util.Map;

/**
 * Service interface for tenant operations.
 */
public interface TenantService {
    Tenant createTenant(TenantRequest request);
    Tenant updateTenant(String id, TenantRequest request);
    Optional<Tenant> findById(String id);
    Optional<Tenant> findBySubdomain(String subdomain);
    List<Tenant> findAll();
    void deleteTenant(String id);
    Tenant updateTenantSettings(String id, TenantSettings settings);
    Map<String, Object> getTenantMetrics(String id);
    // Add more methods as needed
} 