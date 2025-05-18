package com.synchrotech.commandcenter.service.tenant;

import com.synchrotech.commandcenter.model.tenant.Tenant;
import com.synchrotech.commandcenter.dto.request.tenant.TenantRequest;
import com.synchrotech.commandcenter.repository.tenant.TenantRepository;
import com.synchrotech.commandcenter.dto.mapper.TenantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.synchrotech.commandcenter.exception.ResourceNotFoundException;
import com.synchrotech.commandcenter.model.tenant.TenantSettings;
import com.synchrotech.commandcenter.repository.user.UserRepository;
import com.synchrotech.commandcenter.repository.module.InventoryRepository;
import com.synchrotech.commandcenter.util.MongoStatsUtil;
import org.springframework.beans.factory.annotation.Value;
import com.synchrotech.commandcenter.util.ApiCallCounterInterceptor;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

/**
 * Implementation of TenantService.
 */
@Service
public class TenantServiceImpl implements TenantService {
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;
    private final MongoStatsUtil mongoStatsUtil;
    private final ApiCallCounterInterceptor apiCallCounterInterceptor;
    @Value("${spring.data.mongodb.database:synchrotech_dev}")
    private String mongoDbName;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository, UserRepository userRepository, InventoryRepository inventoryRepository, MongoStatsUtil mongoStatsUtil, ApiCallCounterInterceptor apiCallCounterInterceptor) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
        this.mongoStatsUtil = mongoStatsUtil;
        this.apiCallCounterInterceptor = apiCallCounterInterceptor;
    }

    @Override
    public Tenant createTenant(TenantRequest request) {
        String contextSubdomain = com.synchrotech.commandcenter.security.TenantContextHolder.getTenant();
        if (contextSubdomain != null && request.getSubdomain() == null) {
            request.setSubdomain(contextSubdomain);
        }
        Tenant tenant = TenantMapper.toEntity(request);
        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant updateTenant(String id, TenantRequest request) {
        Optional<Tenant> existingOpt = tenantRepository.findById(id);
        if (existingOpt.isEmpty()) throw new ResourceNotFoundException("Tenant not found");
        Tenant existing = existingOpt.get();
        // Update fields
        existing.setName(request.getName());
        existing.setSubdomain(request.getSubdomain());
        existing.setIndustry(request.getIndustry());
        existing.setSettings(request.getSettings() instanceof Map ? (Map<String, Object>) request.getSettings() : null);
        // existing.setBranding(request.getBranding());
        // existing.setEnabledFeatures(request.getEnabledFeatures());
        existing.setUpdatedAt(new Date());
        return tenantRepository.save(existing);
    }

    @Override
    public Optional<Tenant> findById(String id) {
        return tenantRepository.findById(id);
    }

    @Override
    public Optional<Tenant> findBySubdomain(String subdomain) {
        return tenantRepository.findBySubdomain(subdomain);
    }

    @Override
    public List<Tenant> findAll() {
        return tenantRepository.findAll();
    }

    @Override
    public void deleteTenant(String id) {
        if (!tenantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tenant not found");
        }
        tenantRepository.deleteById(id);
    }

    @Override
    public Tenant updateTenantSettings(String id, TenantSettings settings) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        tenant.setSettings(settings instanceof Map ? (Map<String, Object>) settings : null);
        tenant.setUpdatedAt(new Date());
        return tenantRepository.save(tenant);
    }

    @Override
    public Map<String, Object> getTenantMetrics(String id) {
        Tenant tenant = tenantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        Map<String, Object> metrics = new HashMap<>();
        String tenantId = tenant.getSubdomain();
        metrics.put("userCount", userRepository.findByTenantId(tenantId).size());
        metrics.put("inventoryCount", inventoryRepository.findByTenantId(tenantId).size());
        // Storage metrics
        metrics.put("userStorageBytes", mongoStatsUtil.getEstimatedSize(mongoDbName, "users"));
        metrics.put("inventoryStorageBytes", mongoStatsUtil.getEstimatedSize(mongoDbName, "inventory"));
        metrics.put("apiCallVolume", apiCallCounterInterceptor.getApiCallCount(tenantId));
        // Add more metrics as needed
        return metrics;
    }
} 