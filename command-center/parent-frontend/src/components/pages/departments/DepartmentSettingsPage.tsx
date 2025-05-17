import React from 'react';
import { useForm } from 'react-hook-form';
import { Box, Button, TextField, Typography } from '@mui/material';

export default function DepartmentSettingsPage() {
  const { register, handleSubmit, formState: { errors, isSubmitting }, reset } = useForm<{ settings: string }>();

  const onSubmit = async (data: { settings: string }) => {
    // TODO: Call update department settings API
    alert('Department settings saved! (API integration needed)');
    reset();
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Department Settings</Typography>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <TextField
          label="Settings (JSON)"
          fullWidth
          margin="normal"
          {...register('settings', { required: 'Settings are required' })}
          error={!!errors.settings}
          helperText={errors.settings?.message}
        />
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
          {isSubmitting ? 'Saving...' : 'Save Settings'}
        </Button>
      </form>
    </Box>
  );
} 