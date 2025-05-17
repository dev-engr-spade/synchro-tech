export interface RegisterRequest {
  email: string;
  username: string;
  password: string;
  confirmPassword: string;
}

export interface LoginRequest {
  email: string;
  password: string;
  rememberMe?: boolean;
}

export interface AuthResponse {
  token: string;
  refreshToken?: string;
  user: {
    id: string;
    email: string;
    username: string;
    roles: string[];
    tenantId: string;
    mfaEnabled?: boolean;
  };
}

export interface PasswordResetRequest {
  email: string;
}

export interface MfaSetupRequest {
  method: 'email' | 'totp';
}

export interface MfaVerifyRequest {
  code: string;
  method: 'email' | 'totp';
} 