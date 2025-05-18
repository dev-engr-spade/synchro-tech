package com.synchrotech.commandcenter.config.properties;

import com.synchrotech.commandcenter.filter.TenantContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import com.synchrotech.commandcenter.util.ApiCallCounterInterceptor;

@Configuration
public class PropertiesWebConfig implements WebMvcConfigurer {
    @Autowired
    private ApiCallCounterInterceptor apiCallCounterInterceptor;

    @Bean
    public FilterRegistrationBean<TenantContextFilter> tenantContextFilterRegistration(TenantContextFilter filter) {
        FilterRegistrationBean<TenantContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiCallCounterInterceptor).addPathPatterns("/api/**");
    }
} 