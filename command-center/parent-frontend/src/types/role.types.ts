export interface RoleCreateRequest {
  name: string;
  description?: string;
  permissions: string[];
}

export interface RoleUpdateRequest {
  name?: string;
  description?: string;
  permissions?: string[];
}

export interface RoleCloneRequest {
  sourceRoleId: string;
  name: string;
  description?: string;
}

export interface RoleResponse {
  id: string;
  name: string;
  description?: string;
  permissions: string[];
  createdAt: string;
  updatedAt: string;
} 