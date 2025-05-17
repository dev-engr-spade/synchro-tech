export interface DepartmentCreateRequest {
  name: string;
  parentId?: string;
  managerId?: string;
  settings?: Record<string, any>;
}

export interface DepartmentUpdateRequest {
  name?: string;
  parentId?: string;
  managerId?: string;
  settings?: Record<string, any>;
}

export interface DepartmentResponse {
  id: string;
  name: string;
  parentId?: string;
  managerId?: string;
  settings?: Record<string, any>;
  children?: DepartmentResponse[];
  createdAt: string;
  updatedAt: string;
} 