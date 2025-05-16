package com.synchrotech.commandcenter.filter;

import com.synchrotech.commandcenter.security.TenantContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TenantContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String host = httpRequest.getHeader("Host");
        String subdomain = extractSubdomain(host);
        TenantContextHolder.setTenant(subdomain);
        try {
            chain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }

    private String extractSubdomain(String host) {
        if (host == null) return null;
        String[] parts = host.split("\\.");
        if (parts.length < 3) return null; // e.g. tenant.example.com
        return parts[0];
    }
} 