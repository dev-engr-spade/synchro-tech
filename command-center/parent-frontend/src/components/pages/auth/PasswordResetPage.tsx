import React from 'react';
import { useForm } from 'react-hook-form';
import { requestPasswordReset } from '../../../api/services/auth';
import { Box, Button, TextField, Typography } from '@mui/material';

export default function PasswordResetPage() {
  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<{ email: string }>();

  const onSubmit = async (data: { email: string }) => {
    try {
      await requestPasswordReset(data);
      alert('Password reset link sent! Check your email.');
    } catch (err: any) {
      alert(err?.response?.data?.message || 'Failed to send reset link');
    }
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Reset Password</Typography>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          label="Email"
          fullWidth
          margin="normal"
          {...register('email', { required: 'Email is required' })}
          error={!!errors.email}
          helperText={errors.email?.message}
        />
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
          {isSubmitting ? 'Sending...' : 'Send Reset Link'}
        </Button>
      </form>
    </Box>
  );
} 