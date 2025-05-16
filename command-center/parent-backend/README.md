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

