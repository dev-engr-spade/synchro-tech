package com.synchrotech.commandcenter.util;

import com.synchrotech.commandcenter.security.TenantContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ApiCallCounterInterceptor implements HandlerInterceptor {
    private final ConcurrentHashMap<String, AtomicLong> tenantApiCallCounts = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenant = TenantContextHolder.getTenant();
        if (tenant != null) {
            tenantApiCallCounts.computeIfAbsent(tenant, k -> new AtomicLong(0)).incrementAndGet();
        }
        return true;
    }

    public long getApiCallCount(String tenant) {
        return tenantApiCallCounts.getOrDefault(tenant, new AtomicLong(0)).get();
    }
} 