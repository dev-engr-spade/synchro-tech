# Parent Backend

Spring Boot Java application for SynchroTech Command Center. 

## Authentication Flow (Frontend Integration)

### 1. Registration
- **POST /api/auth/register** with `{ username, email, password, tenantId }`
- User receives a verification email with a link: `http://<frontend>/verify?token=...`

### 2. Email Verification
- **Frontend**: User clicks the link, which calls **GET /api/auth/verify?token=...**
- On success, user can now log in.

### 3. Login
- **POST /api/auth/login** with `{ usernameOrEmail, password }`
- On success, response includes:
  - `token`: JWT for API authentication
  - `refreshToken`: for session renewal

### 4. Using Protected APIs
- Add header: `Authorization: Bearer <token>` to all protected API requests.
- If token expires, use refresh flow.

### 5. Refreshing JWT (Remember Me)
- **POST /api/auth/refresh?refreshToken=...**
- On success, response includes new `token` and `refreshToken`.
- Store refresh token securely (e.g., httpOnly cookie or secure storage).

### 6. Logout
- (Optional) Invalidate refresh token on backend (not yet implemented).

## MFA (Multi-Factor Authentication) Flow

### 1. Setup MFA
- **POST /api/auth/mfa/setup** with `{ userId, mfaType: "EMAIL" | "TOTP" }`
- For EMAIL: User receives a code via email.
- For TOTP: User receives a secret to scan with an authenticator app (e.g., Google Authenticator).
- Response includes recovery codes for TOTP.

### 2. Validate MFA
- **POST /api/auth/mfa/validate** with `{ userId, code }`
- For EMAIL: User enters the code received via email.
- For TOTP: User enters the code from their authenticator app.

### 3. Use Recovery Code
- **POST /api/auth/mfa/recovery** with `{ userId, recoveryCode }`
- If user loses access to MFA device, they can use a recovery code (each code is one-time use).

## Example Requests

**Login:**
```json
POST /api/auth/login
{
  "usernameOrEmail": "user@example.com",
  "password": "password123"
}
```
**Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "userId": "...",
  "token": "...",
  "refreshToken": "..."
}
```

**Refresh:**
```json
POST /api/auth/refresh?refreshToken=...
```
**Response:**
```json
{
  "success": true,
  "token": "...",
  "refreshToken": "..."
}
```

**Setup MFA (TOTP):**
```json
POST /api/auth/mfa/setup
{
  "userId": "...",
  "mfaType": "TOTP"
}
```
**Response:**
```json
{
  "success": true,
  "message": "MFA TOTP setup",
  "userId": "...",
  "token": "<TOTP secret>"
}
```

**Validate MFA:**
```json
POST /api/auth/mfa/validate
{
  "userId": "...",
  "code": "123456"
}
```

**Use Recovery Code:**
```json
POST /api/auth/mfa/recovery
{
  "userId": "...",
  "recoveryCode": "abcd1234"
}
```

## Notes
- All protected endpoints require a valid JWT.
- Use refresh tokens for long-lived/"remember me" sessions.
- Roles are included in JWT claims for RBAC.
- See tests for more usage examples.

## TODO NOTES:

What's Added
EmailService: Uses Spring's JavaMailSender to send verification emails.
UserServiceImpl:
After registration, sends a verification email to the user with a unique link.
The verification link is constructed using a configurable frontend URL and the generated token.
Configurable Frontend URL: Uses app.frontend.url property (defaults to http://localhost:3000).
How it works:
When a user registers, they receive an email with a link like:
http://localhost:3000/verify?token=...
The frontend can call the backend /api/auth/verify?token=... endpoint to activate the account.
What you need to do for this to work:
Configure SMTP settings in your application.yml (see example below).
Make sure the frontend handles the verification link and calls the backend.
Example SMTP config for application.yml:

spring:
  mail:
    host: smtp.example.com
    port: 587
    username: your_email@example.com
    password: your_password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

And set your frontend URL:

app:
  frontend:
    url: http://localhost:3000

## User, Department, and Role Management API

### User Endpoints

- **POST /api/users** — Create a new user
- **PUT /api/users/{id}** — Update user details
- **GET /api/users/{id}** — Get user by ID
- **GET /api/users?tenantId=** — List users by tenant
- **DELETE /api/users/{id}** — Delete user
- **POST /api/users/bulk** — Bulk create users
- **PUT /api/users/{id}/profile** — Update user profile
- **PUT /api/users/{id}/status?active=** — Activate/deactivate user

**Example: Create User**
```json
POST /api/users
{
  "username": "jdoe",
  "email": "jdoe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890",
  "profilePictureUrl": "https://example.com/avatar.jpg",
  "departmentId": "dep123",
  "roleIds": ["role1", "role2"],
  "tenantId": "tenant1",
  "active": true
}
```
**Response:**
```json
{
  "id": "user123",
  "username": "jdoe",
  "email": "jdoe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "+1234567890",
  "profilePictureUrl": "https://example.com/avatar.jpg",
  "departmentId": "dep123",
  "roles": ["role1", "role2"],
  "tenantId": "tenant1",
  "active": true,
  "createdAt": 1710000000000,
  "updatedAt": 1710000000000
}
```

### Department Endpoints

- **POST /api/departments** — Create department
- **PUT /api/departments/{id}** — Update department
- **DELETE /api/departments/{id}** — Delete department
- **GET /api/departments/{id}** — Get department by ID
- **GET /api/departments?tenantId=** — List departments by tenant
- **GET /api/departments/parent/{parentId}** — List departments by parent
- **POST /api/departments/assign-manager** — Assign manager to department

**Example: Create Department**
```json
POST /api/departments
{
  "name": "Engineering",
  "parentId": null,
  "managerUserId": "user123",
  "tenantId": "tenant1",
  "description": "Engineering department"
}
```
**Response:**
```json
{
  "id": "dep123",
  "name": "Engineering",
  "tenantId": "tenant1",
  "parentId": null,
  "childIds": [],
  "managerUserId": "user123",
  "settings": {
    "description": "Engineering department",
    "active": true
  },
  "createdAt": 1710000000000,
  "updatedAt": 1710000000000
}
```

### Role Endpoints

- **POST /api/admin/roles** — Create role
- **PUT /api/admin/roles/{id}** — Update role
- **POST /api/admin/roles/clone** — Clone role
- **DELETE /api/admin/roles/{id}** — Delete role
- **GET /api/admin/roles** — List roles
- **GET /api/admin/roles/{id}** — Get role by ID
- **POST /api/admin/roles/permissions** — Create permission
- **PUT /api/admin/roles/permissions/{id}** — Update permission
- **DELETE /api/admin/roles/permissions/{id}** — Delete permission
- **GET /api/admin/roles/permissions** — List permissions
- **GET /api/admin/roles/permissions/{id}** — Get permission by ID
- **PUT /api/admin/roles/assign/{userId}** — Assign roles to user

**Example: Create Role**
```json
POST /api/admin/roles
{
  "name": "Manager",
  "description": "Department manager role",
  "permissionIds": ["perm1", "perm2"],
  "tenantId": "tenant1"
}
```
**Response:**
```json
{
  "id": "role1",
  "name": "Manager",
  "description": "Department manager role",
  "permissionIds": ["perm1", "perm2"],
  "tenantId": "tenant1",
  "createdAt": 1710000000000,
  "updatedAt": 1710000000000
}
```

### New Features

#### Role Audit Log Endpoints

- **GET /api/admin/roles/audit/{roleId}** — Get audit logs for a role
- **GET /api/admin/roles/audit/tenant/{tenantId}** — Get audit logs for all roles in a tenant

**Example Response:**
```json
[
  {
    "id": "log1",
    "roleId": "role1",
    "action": "UPDATE",
    "performedBy": "system",
    "tenantId": "tenant1",
    "details": "Updated role: Manager",
    "timestamp": 1710000000000
  },
  {
    "id": "log2",
    "roleId": "role1",
    "action": "CLONE",
    "performedBy": "system",
    "tenantId": "tenant1",
    "details": "Cloned from role: role0",
    "timestamp": 1710000001000
  }
]
```

#### Profile Picture Upload Endpoint

- **POST /api/users/{id}/profile-picture** (multipart/form-data, param: file)

**Example Request:**
```
curl -X POST -F "file=@/path/to/pic.jpg" http://localhost:8080/api/users/user123/profile-picture
```
**Example Response:**
```
"/uploads/profile-pictures/user123_1710000000000_pic.jpg"
```

### S3 Profile Picture Upload Configuration

To use Amazon S3 for profile picture uploads:

1. **Set your application.yml:**

```yaml
app:
  profile-picture:
    storage: s3
    s3:
      bucket: your-bucket-name
      region: us-east-1
      base-url: https://your-bucket-name.s3.amazonaws.com/
```

2. **Set AWS credentials:**
   - Use environment variables (`AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`), EC2 instance roles, or AWS CLI config.

3. **Required IAM Policy Example:**
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": ["s3:PutObject", "s3:GetObject"],
      "Resource": "arn:aws:s3:::your-bucket-name/profile-pictures/*"
    }
  ]
}
```

4. **Usage:**
   - The upload endpoint `/api/users/{id}/profile-picture` will store the file in S3 and return the public URL.
   - The URL is saved in the user's `profilePictureUrl` field.

5. **Troubleshooting:**
   - Ensure your bucket is in the correct region and is accessible.
   - Make sure your credentials have the right permissions.
   - The returned URL is based on the `base-url` property; adjust if using a custom domain or CloudFront.

