import React from 'react';
import { useQuery } from 'react-query';
import { getTenantResourceUsage } from '../../../api/services/tenants';
import { Box, Typography, Card, CardContent, CircularProgress, Grid } from '@mui/material';

export default function TenantResourceUsagePage({ tenantId }: { tenantId: string }) {
  const { data, isLoading, error } = useQuery(['tenantUsage', tenantId], () => getTenantResourceUsage(tenantId));

  if (isLoading) return <CircularProgress />;
  if (error) return <Typography color="error">Failed to load resource usage</Typography>;

  return (
    <Box mt={4}>
      <Typography variant="h5" mb={2}>Tenant Resource Usage</Typography>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Typography variant="subtitle1">Storage Used</Typography>
              <Typography variant="h6">{data.storageUsed} MB</Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Typography variant="subtitle1">API Calls</Typography>
              <Typography variant="h6">{data.apiCalls}</Typography>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <Card>
            <CardContent>
              <Typography variant="subtitle1">User Count</Typography>
              <Typography variant="h6">{data.userCount}</Typography>
            </CardContent>
          </Card>
        </Grid>
        {/* Add more metrics as needed */}
      </Grid>
    </Box>
  );
} 