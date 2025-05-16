package com.synchrotech.commandcenter.model.tenant;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantSettings {
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String timezone;
    private String locale;
    private String preferencesJson; // For extensible preferences
} 