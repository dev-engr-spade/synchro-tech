import React from 'react';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { tenantCreateSchema } from '../../../utils/validation';
import { TenantCreateRequest } from '../../../types/tenant.types';
import { createTenant } from '../../../api/services/tenants';
import { Box, Button, TextField, Typography, MenuItem } from '@mui/material';

const industries = [
  'Retail', 'Construction', 'Consultancy', 'Accounting', 'Project Management', 'Task Management', 'Booking', 'Real Estate'
];

export default function TenantCreatePage() {
  const { register, handleSubmit, formState: { errors, isSubmitting }, reset } = useForm<TenantCreateRequest>({
    resolver: yupResolver(tenantCreateSchema),
  });

  const onSubmit = async (data: TenantCreateRequest) => {
    try {
      await createTenant(data);
      alert('Tenant created successfully!');
      reset();
    } catch (err: any) {
      alert(err?.response?.data?.message || 'Failed to create tenant');
    }
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Create New Tenant</Typography>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          label="Tenant Name"
          fullWidth
          margin="normal"
          {...register('name')}
          error={!!errors.name}
          helperText={errors.name?.message}
        />
        <TextField
          label="Subdomain"
          fullWidth
          margin="normal"
          {...register('subdomain')}
          error={!!errors.subdomain}
          helperText={errors.subdomain?.message}
        />
        <TextField
          label="Industry"
          select
          fullWidth
          margin="normal"
          {...register('industry')}
          error={!!errors.industry}
          helperText={errors.industry?.message}
        >
          {industries.map((ind) => (
            <MenuItem key={ind} value={ind}>{ind}</MenuItem>
          ))}
        </TextField>
        {/* Optionally add theme/feature fields here */}
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
          {isSubmitting ? 'Creating...' : 'Create Tenant'}
        </Button>
      </form>
    </Box>
  );
} 