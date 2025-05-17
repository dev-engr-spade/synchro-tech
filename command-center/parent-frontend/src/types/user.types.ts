export interface UserCreateRequest {
  username: string;
  email: string;
  password: string;
  departmentId?: string;
  roles?: string[];
  status?: 'active' | 'inactive';
}

export interface UserUpdateRequest {
  username?: string;
  email?: string;
  departmentId?: string;
  roles?: string[];
  status?: 'active' | 'inactive';
}

export interface UserBulkCreateRequest {
  users: UserCreateRequest[];
}

export interface UserResponse {
  id: string;
  username: string;
  email: string;
  departmentId?: string;
  roles: string[];
  status: 'active' | 'inactive';
  createdAt: string;
  updatedAt: string;
} 