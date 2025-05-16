package com.synchrotech.commandcenter.dto.request.tenant;

import com.synchrotech.commandcenter.model.tenant.TenantSettings;
import com.synchrotech.commandcenter.model.tenant.Branding;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * DTO for tenant requests.
 */
@Data
public class TenantRequest {
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String subdomain;

    @NotBlank
    private String industry;

    private TenantSettings settings;
    private Branding branding;
    private List<String> enabledFeatures;
} 