import React from 'react';
import { useForm } from 'react-hook-form';
import { Box, Button, TextField, Typography, MenuItem } from '@mui/material';
import { UserCreateRequest } from '../../../types/user.types';

const statuses = ['active', 'inactive'];

export default function UserFormPage() {
  const { register, handleSubmit, formState: { errors, isSubmitting }, reset } = useForm<UserCreateRequest>();

  const onSubmit = async (data: UserCreateRequest) => {
    // TODO: Call create or update user API
    alert('User saved! (API integration needed)');
    reset();
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>User Form</Typography>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          label="Username"
          fullWidth
          margin="normal"
          {...register('username', { required: 'Username is required' })}
          error={!!errors.username}
          helperText={errors.username?.message}
        />
        <TextField
          label="Email"
          fullWidth
          margin="normal"
          {...register('email', { required: 'Email is required' })}
          error={!!errors.email}
          helperText={errors.email?.message}
        />
        <TextField
          label="Password"
          type="password"
          fullWidth
          margin="normal"
          {...register('password')}
        />
        <TextField
          label="Department ID"
          fullWidth
          margin="normal"
          {...register('departmentId')}
        />
        <TextField
          label="Roles (comma separated)"
          fullWidth
          margin="normal"
          {...register('roles')}
        />
        <TextField
          label="Status"
          select
          fullWidth
          margin="normal"
          {...register('status')}
        >
          {statuses.map((status) => (
            <MenuItem key={status} value={status}>{status}</MenuItem>
          ))}
        </TextField>
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
          {isSubmitting ? 'Saving...' : 'Save User'}
        </Button>
      </form>
    </Box>
  );
} 