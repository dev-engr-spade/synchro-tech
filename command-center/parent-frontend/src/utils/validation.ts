import * as yup from 'yup';

export const tenantCreateSchema = yup.object().shape({
  name: yup.string().required('Tenant name is required'),
  subdomain: yup
    .string()
    .matches(/^[a-z0-9-]+$/, 'Subdomain must be lowercase letters, numbers, or hyphens')
    .required('Subdomain is required'),
  industry: yup.string().required('Industry is required'),
});

export const userRegistrationSchema = yup.object().shape({
  email: yup.string().email('Invalid email').required('Email is required'),
  password: yup.string().min(8, 'Password must be at least 8 characters').required('Password is required'),
  confirmPassword: yup
    .string()
    .oneOf([yup.ref('password'), null], 'Passwords must match')
    .required('Confirm password is required'),
  username: yup.string().required('Username is required'),
}); 