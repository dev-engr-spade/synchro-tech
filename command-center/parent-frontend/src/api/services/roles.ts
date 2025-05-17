import api from '../axios';
import { RoleCreateRequest, RoleResponse, RoleUpdateRequest, RoleCloneRequest } from '../../types/role.types';

export const createRole = async (data: RoleCreateRequest): Promise<RoleResponse> => {
  const res = await api.post('/roles', data);
  return res.data;
};

export const updateRole = async (roleId: string, data: RoleUpdateRequest): Promise<RoleResponse> => {
  const res = await api.put(`/roles/${roleId}`, data);
  return res.data;
};

export const getRoles = async (): Promise<RoleResponse[]> => {
  const res = await api.get('/roles');
  return res.data;
};

export const getRole = async (roleId: string): Promise<RoleResponse> => {
  const res = await api.get(`/roles/${roleId}`);
  return res.data;
};

export const cloneRole = async (data: RoleCloneRequest): Promise<RoleResponse> => {
  const res = await api.post('/roles/clone', data);
  return res.data;
};

export const getRoleAuditTrail = async (roleId: string): Promise<any[]> => {
  const res = await api.get(`/roles/${roleId}/audit`);
  return res.data;
}; 