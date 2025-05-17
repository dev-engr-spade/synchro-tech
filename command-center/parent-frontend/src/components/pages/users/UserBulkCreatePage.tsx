import React from 'react';
import { useForm } from 'react-hook-form';
import { Box, Button, TextField, Typography } from '@mui/material';

export default function UserBulkCreatePage() {
  const { register, handleSubmit, formState: { errors, isSubmitting }, reset } = useForm<{ users: string }>();

  const onSubmit = async (data: { users: string }) => {
    // TODO: Call bulk create users API
    alert('Bulk users created! (API integration needed)');
    reset();
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Bulk User Creation</Typography>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          label="Users (CSV or JSON)"
          fullWidth
          margin="normal"
          multiline
          minRows={6}
          {...register('users', { required: 'Users data is required' })}
          error={!!errors.users}
          helperText={errors.users?.message}
        />
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
          {isSubmitting ? 'Creating...' : 'Create Users'}
        </Button>
      </form>
    </Box>
  );
} 