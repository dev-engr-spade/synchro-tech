import React from 'react';
import { useForm } from 'react-hook-form';
import { LoginRequest } from '../../../types/auth.types';
import { login } from '../../../api/services/auth';
import { Box, Button, TextField, Typography, FormControlLabel, Checkbox } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<LoginRequest>();
  const navigate = useNavigate();

  const onSubmit = async (data: LoginRequest) => {
    try {
      const res = await login(data);
      localStorage.setItem('token', res.token);
      localStorage.setItem('tenantId', res.user.tenantId);
      navigate('/users');
    } catch (err: any) {
      alert(err?.response?.data?.message || 'Login failed');
    }
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Login</Typography>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
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
          {...register('password', { required: 'Password is required' })}
          error={!!errors.password}
          helperText={errors.password?.message}
        />
        <FormControlLabel
          control={<Checkbox {...register('rememberMe')} />}
          label="Remember me"
        />
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
          {isSubmitting ? 'Logging in...' : 'Login'}
        </Button>
      </form>
    </Box>
  );
} 