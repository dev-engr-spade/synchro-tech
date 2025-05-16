package com.synchrotech.commandcenter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synchrotech.commandcenter.dto.request.tenant.TenantRequest;
import com.synchrotech.commandcenter.model.tenant.Tenant;
import com.synchrotech.commandcenter.service.tenant.TenantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TenantController.class)
class TenantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TenantService tenantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createTenant_validRequest_returnsOk() throws Exception {
        TenantRequest request = new TenantRequest();
        request.setName("Test Tenant");
        request.setSubdomain("test");
        request.setIndustry("Retail");

        Tenant tenant = Tenant.builder().id("1").name("Test Tenant").subdomain("test").industry("Retail").build();
        Mockito.when(tenantService.createTenant(any())).thenReturn(tenant);

        mockMvc.perform(post("/api/tenants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Tenant"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createTenant_invalidRequest_returnsBadRequest() throws Exception {
        TenantRequest request = new TenantRequest(); // missing required fields

        mockMvc.perform(post("/api/tenants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllTenants_returnsList() throws Exception {
        Mockito.when(tenantService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/tenants"))
                .andExpect(status().isOk());
    }
}
