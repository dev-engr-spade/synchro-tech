import React from 'react';
import { useForm } from 'react-hook-form';
import { Box, Button, TextField, Typography } from '@mui/material';
import { DepartmentCreateRequest } from '../../../types/department.types';

export default function DepartmentFormPage() {
  const { register, handleSubmit, formState: { errors, isSubmitting }, reset } = useForm<DepartmentCreateRequest>();

  const onSubmit = async (data: DepartmentCreateRequest) => {
    // TODO: Call create or update department API
    alert('Department saved! (API integration needed)');
    reset();
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Department Form</Typography>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          label="Department Name"
          fullWidth
          margin="normal"
          {...register('name', { required: 'Name is required' })}
          error={!!errors.name}
          helperText={errors.name?.message}
        />
        <TextField
          label="Parent Department ID"
          fullWidth
          margin="normal"
          {...register('parentId')}
        />
        <TextField
          label="Manager ID"
          fullWidth
          margin="normal"
          {...register('managerId')}
        />
        <TextField
          label="Settings (JSON)"
          fullWidth
          margin="normal"
          {...register('settings')}
        />
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
          {isSubmitting ? 'Saving...' : 'Save Department'}
        </Button>
      </form>
    </Box>
  );
} 