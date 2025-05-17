import React from 'react';
import { useForm } from 'react-hook-form';
import { Box, Button, TextField, Typography, MenuItem } from '@mui/material';
import { updateTenantSettings } from '../../../api/services/tenants';
import { TenantSettingsUpdateRequest } from '../../../types/tenant.types';

const timezones = ['UTC', 'America/New_York', 'Europe/London', 'Asia/Tokyo'];
const locales = ['en', 'es', 'fr', 'de', 'zh'];

export default function TenantSettingsPage() {
  const { register, handleSubmit, formState: { errors, isSubmitting }, reset } = useForm<TenantSettingsUpdateRequest>();

  const onSubmit = async (data: TenantSettingsUpdateRequest) => {
    try {
      // Replace 'tenantId' with actual tenant id from context or route
      await updateTenantSettings('tenantId', data);
      alert('Tenant settings updated!');
      reset();
    } catch (err: any) {
      alert(err?.response?.data?.message || 'Failed to update tenant settings');
    }
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Tenant Settings</Typography>
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
          label="Contact Email"
          fullWidth
          margin="normal"
          {...register('contactEmail')}
          error={!!errors.contactEmail}
          helperText={errors.contactEmail?.message}
        />
        <TextField
          label="Timezone"
          select
          fullWidth
          margin="normal"
          {...register('timezone')}
          error={!!errors.timezone}
          helperText={errors.timezone?.message}
        >
          {timezones.map((tz) => (
            <MenuItem key={tz} value={tz}>{tz}</MenuItem>
          ))}
        </TextField>
        <TextField
          label="Locale"
          select
          fullWidth
          margin="normal"
          {...register('locale')}
          error={!!errors.locale}
          helperText={errors.locale?.message}
        >
          {locales.map((loc) => (
            <MenuItem key={loc} value={loc}>{loc}</MenuItem>
          ))}
        </TextField>
        <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
          {isSubmitting ? 'Saving...' : 'Save Settings'}
        </Button>
      </form>
    </Box>
  );
} 