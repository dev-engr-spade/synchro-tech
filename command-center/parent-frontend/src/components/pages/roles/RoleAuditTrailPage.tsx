import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { getRoleAuditTrail } from '../../../api/services/roles';
import { Box, Typography, List, ListItem, ListItemText, CircularProgress } from '@mui/material';

export default function RoleAuditTrailPage({ roleId }: { roleId: string }) {
  const { data, isLoading, error } = useQuery(['roleAudit', roleId], () => getRoleAuditTrail(roleId));

  if (isLoading) return <CircularProgress />;
  if (error) return <Typography color="error">Failed to load audit trail</Typography>;

  return (
    <Box mt={4}>
      <Typography variant="h5" mb={2}>Role Audit Trail</Typography>
      <List>
        {data?.map((entry: any, idx: number) => (
          <ListItem key={idx}>
            <ListItemText
              primary={entry.action}
              secondary={`By: ${entry.actor} at ${new Date(entry.timestamp).toLocaleString()}`}
            />
          </ListItem>
        ))}
      </List>
    </Box>
  );
} 