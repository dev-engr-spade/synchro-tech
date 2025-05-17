import React from 'react';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { userRegistrationSchema } from '../../../utils/validation';
import { RegisterRequest } from '../../../types/auth.types';
import { register as registerUser } from '../../../api/services/auth';
import { Box, Button, TextField, Typography } from '@mui/material';

export default function RegisterPage() {
  const { register, handleSubmit, formState: { errors, isSubmitting }, reset } = useForm<RegisterRequest>({
    resolver: yupResolver(userRegistrationSchema),
  });

  const onSubmit = async (data: RegisterRequest) => {
    try {
      await registerUser(data);
      alert('Registration successful! Please check your email to verify your account.');
      reset();
    } catch (err: any) {
      alert(err?.response?.data?.message || 'Registration failed');
    }
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Register</Typography>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          label="Username"
          fullWidth
          margin="normal"
          {...register('username')}
          error={!!errors.username}
          helperText={errors.username?.message}
        />
        <TextField
          label="Email"
          fullWidth
          margin="normal"
          {...register('email')}
          error={!!errors.email}
          helperText={errors.email?.message}
        />
        <TextField
          label="Password"
          type="password"
          fullWidth
          margin="normal"
          {...register('password')}
          error={!!errors.password}
          helperText={errors.password?.message}
        />
        <TextField
          label="Confirm Password"
          type="password"
          fullWidth
          margin="normal"
          {...register('confirmPassword')}
          error={!!errors.confirmPassword}
          helperText={errors.confirmPassword?.message}
        />
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
          {isSubmitting ? 'Registering...' : 'Register'}
        </Button>
      </form>
    </Box>
  );
} 