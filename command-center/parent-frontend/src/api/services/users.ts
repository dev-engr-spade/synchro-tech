import api from '../axios';
import { UserCreateRequest, UserResponse, UserUpdateRequest, UserBulkCreateRequest } from '../../types/user.types';

export const createUser = async (data: UserCreateRequest): Promise<UserResponse> => {
  const res = await api.post('/users', data);
  return res.data;
};

export const updateUser = async (userId: string, data: UserUpdateRequest): Promise<UserResponse> => {
  const res = await api.put(`/users/${userId}`, data);
  return res.data;
};

export const getUser = async (userId: string): Promise<UserResponse> => {
  const res = await api.get(`/users/${userId}`);
  return res.data;
};

export const getUsers = async (): Promise<UserResponse[]> => {
  const res = await api.get('/users');
  return res.data;
};

export const deactivateUser = async (userId: string): Promise<void> => {
  await api.patch(`/users/${userId}/deactivate`);
};

export const reactivateUser = async (userId: string): Promise<void> => {
  await api.patch(`/users/${userId}/reactivate`);
};

export const bulkCreateUsers = async (data: UserBulkCreateRequest): Promise<UserResponse[]> => {
  const res = await api.post('/users/bulk', data);
  return res.data;
}; 