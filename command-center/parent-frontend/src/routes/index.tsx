import React from 'react';
import { BrowserRouter, Routes, Route, Navigate, useParams } from 'react-router-dom';
import MainLayout from '../layouts/MainLayout';
import AuthLayout from '../layouts/AuthLayout';
import TenantCreatePage from '../components/pages/tenants/TenantCreatePage';
import RegisterPage from '../components/pages/auth/RegisterPage';
import LoginPage from '../components/pages/auth/LoginPage';
import PasswordResetPage from '../components/pages/auth/PasswordResetPage';
import MfaSetupPage from '../components/pages/auth/MfaSetupPage';
import UserListPage from '../components/pages/users/UserListPage';
import UserProfilePage from '../components/pages/users/UserProfilePage';
import DepartmentListPage from '../components/pages/departments/DepartmentListPage';
import RoleListPage from '../components/pages/roles/RoleListPage';
import RoleAuditTrailPage from '../components/pages/roles/RoleAuditTrailPage';
import TenantResourceUsagePage from '../components/pages/tenants/TenantResourceUsagePage';

function RoleAuditTrailRoute() {
  const { roleId } = useParams();
  return roleId ? <RoleAuditTrailPage roleId={roleId} /> : null;
}

function TenantResourceUsageRoute() {
  const { tenantId } = useParams();
  return tenantId ? <TenantResourceUsagePage tenantId={tenantId} /> : null;
}

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Public/Auth routes */}
        <Route element={<AuthLayout />}>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/password-reset" element={<PasswordResetPage />} />
          <Route path="/mfa-setup" element={<MfaSetupPage />} />
        </Route>
        {/* Main app routes */}
        <Route element={<MainLayout />}>
          <Route path="/tenants/create" element={<TenantCreatePage />} />
          <Route path="/users" element={<UserListPage />} />
          <Route path="/profile" element={<UserProfilePage />} />
          <Route path="/departments" element={<DepartmentListPage />} />
          <Route path="/roles" element={<RoleListPage />} />
          <Route path="/roles/:roleId/audit" element={<RoleAuditTrailRoute />} />
          <Route path="/tenants/:tenantId/usage" element={<TenantResourceUsageRoute />} />
        </Route>
        <Route path="*" element={<Navigate to="/users" replace />} />
      </Routes>
    </BrowserRouter>
  );
} 