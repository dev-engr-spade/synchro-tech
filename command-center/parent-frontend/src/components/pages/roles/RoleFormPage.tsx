import React from 'react';
import { useForm } from 'react-hook-form';
import { Box, Button, TextField, Typography } from '@mui/material';
import { RoleCreateRequest } from '../../../types/role.types';

export default function RoleFormPage() {
  const { register, handleSubmit, formState: { errors, isSubmitting }, reset } = useForm<RoleCreateRequest>();

  const onSubmit = async (data: RoleCreateRequest) => {
    // TODO: Call create or update role API
    alert('Role saved! (API integration needed)');
    reset();
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Role Form</Typography>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          label="Role Name"
          fullWidth
          margin="normal"
          {...register('name', { required: 'Name is required' })}
          error={!!errors.name}
          helperText={errors.name?.message}
        />
        <TextField
          label="Description"
          fullWidth
          margin="normal"
          {...register('description')}
        />
        <TextField
          label="Permissions (comma separated)"
          fullWidth
          margin="normal"
          {...register('permissions')}
        />
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
          {isSubmitting ? 'Saving...' : 'Save Role'}
        </Button>
      </form>
    </Box>
  );
} 