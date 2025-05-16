package com.synchrotech.commandcenter.service.tenant;

import com.synchrotech.commandcenter.dto.request.tenant.TenantRequest;
import com.synchrotech.commandcenter.exception.ResourceNotFoundException;
import com.synchrotech.commandcenter.model.tenant.Tenant;
import com.synchrotech.commandcenter.model.tenant.TenantSettings;
import com.synchrotech.commandcenter.repository.tenant.TenantRepository;
import com.synchrotech.commandcenter.repository.user.UserRepository;
import com.synchrotech.commandcenter.repository.module.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TenantServiceImplTest {

    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private TenantServiceImpl tenantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTenant_success() {
        TenantRequest request = new TenantRequest();
        request.setName("Test Tenant");
        request.setSubdomain("test");
        request.setIndustry("Retail");

        Tenant tenant = Tenant.builder()
                .id("1")
                .name("Test Tenant")
                .subdomain("test")
                .industry("Retail")
                .build();

        when(tenantRepository.save(any(Tenant.class))).thenReturn(tenant);

        Tenant result = tenantService.createTenant(request);
        assertEquals("Test Tenant", result.getName());
        verify(tenantRepository).save(any(Tenant.class));
    }

    @Test
    void updateTenant_notFound() {
        when(tenantRepository.findById("1")).thenReturn(Optional.empty());
        TenantRequest request = new TenantRequest();
        assertThrows(ResourceNotFoundException.class, () -> tenantService.updateTenant("1", request));
    }

    @Test
    void updateTenantSettings_success() {
        TenantSettings settings = TenantSettings.builder().contactEmail("admin@x.com").build();
        Tenant tenant = Tenant.builder().id("1").settings(settings).build();
        when(tenantRepository.findById("1")).thenReturn(Optional.of(tenant));
        when(tenantRepository.save(any(Tenant.class))).thenReturn(tenant);

        Tenant updated = tenantService.updateTenantSettings("1", settings);
        assertEquals("admin@x.com", updated.getSettings().getContactEmail());
    }

    @Test
    void getTenantMetrics_success() {
        Tenant tenant = Tenant.builder().id("1").subdomain("test").build();
        when(tenantRepository.findById("1")).thenReturn(Optional.of(tenant));
        when(userRepository.findByTenantId("test")).thenReturn(Collections.emptyList());
        when(inventoryRepository.findByTenantId("test")).thenReturn(Collections.emptyList());

        Map<String, Object> metrics = tenantService.getTenantMetrics("1");
        assertEquals(0, metrics.get("userCount"));
        assertEquals(0, metrics.get("inventoryCount"));
    }
} 