import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { getRoles } from '../../../api/services/roles';
import { RoleResponse } from '../../../types/role.types';
import { Box, Typography, Table, TableHead, TableRow, TableCell, TableBody, CircularProgress } from '@mui/material';

export default function RoleListPage() {
  const { data, isLoading, error } = useQuery<RoleResponse[]>(['roles'], getRoles);

  if (isLoading) return <CircularProgress />;
  if (error) return <Typography color="error">Failed to load roles</Typography>;

  return (
    <Box mt={4}>
      <Typography variant="h5" mb={2}>Roles</Typography>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>Description</TableCell>
            <TableCell>Permissions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data?.map((role) => (
            <TableRow key={role.id}>
              <TableCell>{role.name}</TableCell>
              <TableCell>{role.description}</TableCell>
              <TableCell>{role.permissions.join(', ')}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Box>
  );
} 