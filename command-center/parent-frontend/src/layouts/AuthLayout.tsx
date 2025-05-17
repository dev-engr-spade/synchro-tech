import React from 'react';
import { Outlet } from 'react-router-dom';
import { Box, Container, Paper, Toolbar, Typography } from '@mui/material';

export default function AuthLayout() {
  return (
    <Box minHeight="100vh" display="flex" flexDirection="column" justifyContent="center" alignItems="center" bgcolor="#f5f5f5">
      <Toolbar />
      <Container maxWidth="sm">
        <Paper elevation={3} sx={{ p: 4, mt: 8 }}>
          <Typography variant="h4" align="center" mb={2}>Welcome</Typography>
          <Outlet />
        </Paper>
      </Container>
    </Box>
  );
} 