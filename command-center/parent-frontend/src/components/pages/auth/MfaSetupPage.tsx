import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { setupMfa, verifyMfa } from '../../../api/services/auth';
import { Box, Button, TextField, Typography } from '@mui/material';

export default function MfaSetupPage() {
  const [step, setStep] = useState<'choose' | 'verify'>('choose');
  const [method, setMethod] = useState<'email' | 'totp' | ''>('');
  const [secret, setSecret] = useState<string | null>(null);
  const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<{ code: string }>();

  const handleChoose = async (chosen: 'email' | 'totp') => {
    setMethod(chosen);
    const res = await setupMfa({ method: chosen });
    setSecret(res.secret || null);
    setStep('verify');
  };

  const onSubmit = async (data: { code: string }) => {
    try {
      await verifyMfa({ code: data.code, method });
      alert('MFA setup complete!');
    } catch (err: any) {
      alert(err?.response?.data?.message || 'Failed to verify MFA');
    }
  };

  return (
    <Box maxWidth={400} mx="auto" mt={4}>
      <Typography variant="h5" mb={2}>Multi-Factor Authentication Setup</Typography>
      {step === 'choose' && (
        <>
          <Button fullWidth variant="contained" sx={{ mb: 2 }} onClick={() => handleChoose('email')}>Setup via Email</Button>
          <Button fullWidth variant="outlined" onClick={() => handleChoose('totp')}>Setup via Authenticator App</Button>
        </>
      )}
      {step === 'verify' && (
        <form onSubmit={handleSubmit(onSubmit)} noValidate>
          {method === 'totp' && secret && (
            <Typography mb={2}>Scan this QR or enter secret: <b>{secret}</b></Typography>
          )}
          <TextField
            label="Verification Code"
            fullWidth
            margin="normal"
            {...register('code', { required: 'Code is required' })}
            error={!!errors.code}
            helperText={errors.code?.message}
          />
          <Button type="submit" variant="contained" color="primary" fullWidth disabled={isSubmitting}>
            {isSubmitting ? 'Verifying...' : 'Verify'}
          </Button>
        </form>
      )}
    </Box>
  );
} 