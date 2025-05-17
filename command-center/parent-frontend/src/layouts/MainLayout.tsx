import React from 'react';
import { Outlet, Link } from 'react-router-dom';
import { AppBar, Toolbar, Typography, Drawer, List, ListItem, ListItemText, Box } from '@mui/material';

const drawerWidth = 200;

const navItems = [
  { label: 'Users', path: '/users' },
  { label: 'Departments', path: '/departments' },
  { label: 'Roles', path: '/roles' },
  { label: 'Profile', path: '/profile' },
  { label: 'Create Tenant', path: '/tenants/create' },
];

export default function MainLayout() {
  return (
    <Box sx={{ display: 'flex' }}>
      <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
        <Toolbar>
          <Typography variant="h6" noWrap component="div">
            Synchro Tech Command Center
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
        }}
      >
        <Toolbar />
        <List>
          {navItems.map((item) => (
            <ListItem button key={item.path} component={Link} to={item.path}>
              <ListItemText primary={item.label} />
            </ListItem>
          ))}
        </List>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3, ml: `${drawerWidth}px` }}>
        <Toolbar />
        <Outlet />
      </Box>
    </Box>
  );
} 