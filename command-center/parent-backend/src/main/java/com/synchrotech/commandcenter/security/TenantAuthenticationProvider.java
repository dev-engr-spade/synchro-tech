package com.synchrotech.commandcenter.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

/**
 * Custom authentication provider for multi-tenancy.
 */
@Component
public class TenantAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
} 