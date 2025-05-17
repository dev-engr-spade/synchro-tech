import api from '../axios';
import { RegisterRequest, LoginRequest, AuthResponse, PasswordResetRequest, MfaSetupRequest, MfaVerifyRequest } from '../../types/auth.types';

export const register = async (data: RegisterRequest): Promise<AuthResponse> => {
  const res = await api.post('/auth/register', data);
  return res.data;
};

export const login = async (data: LoginRequest): Promise<AuthResponse> => {
  const res = await api.post('/auth/login', data);
  return res.data;
};

export const logout = async (): Promise<void> => {
  await api.post('/auth/logout');
};

export const requestPasswordReset = async (data: PasswordResetRequest): Promise<void> => {
  await api.post('/auth/password-reset/request', data);
};

export const resetPassword = async (token: string, password: string): Promise<void> => {
  await api.post(`/auth/password-reset/confirm`, { token, password });
};

export const setupMfa = async (data: MfaSetupRequest): Promise<any> => {
  const res = await api.post('/auth/mfa/setup', data);
  return res.data;
};

export const verifyMfa = async (data: MfaVerifyRequest): Promise<any> => {
  const res = await api.post('/auth/mfa/verify', data);
  return res.data;
}; 