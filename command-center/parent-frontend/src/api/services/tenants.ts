import api from '../axios';
import { TenantCreateRequest, TenantResponse, TenantSettingsUpdateRequest } from '../../types/tenant.types';

export const createTenant = async (data: TenantCreateRequest): Promise<TenantResponse> => {
  const res = await api.post('/tenants', data);
  return res.data;
};

export const updateTenantSettings = async (tenantId: string, data: TenantSettingsUpdateRequest): Promise<TenantResponse> => {
  const res = await api.put(`/tenants/${tenantId}/settings`, data);
  return res.data;
};

export const getTenant = async (tenantId: string): Promise<TenantResponse> => {
  const res = await api.get(`/tenants/${tenantId}`);
  return res.data;
};

export const getTenantResourceUsage = async (tenantId: string) => {
  const res = await api.get(`/tenants/${tenantId}/usage`);
  return res.data;
}; 