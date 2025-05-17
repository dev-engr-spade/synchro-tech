export interface TenantCreateRequest {
  name: string;
  subdomain: string;
  industry: string;
  theme?: {
    primaryColor?: string;
    logoUrl?: string;
    [key: string]: any;
  };
  features?: string[];
}

export interface TenantResponse {
  id: string;
  name: string;
  subdomain: string;
  industry: string;
  theme: Record<string, any>;
  features: string[];
  createdAt: string;
  updatedAt: string;
}

export interface TenantSettingsUpdateRequest {
  name?: string;
  contactEmail?: string;
  timezone?: string;
  locale?: string;
  preferences?: Record<string, any>;
} 