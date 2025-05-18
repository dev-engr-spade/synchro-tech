package com.synchrotech.commandcenter.service.core;

import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {
    public void updateSetting(String tenantId, String key, String value) {
        System.out.printf("Setting updated for tenant %s: %s = %s%n", tenantId, key, value);
    }
} 