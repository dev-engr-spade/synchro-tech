# Technical Specification: Synchro Tech - Command Center
## Project Overview
Command Center is a multi-tenant, multi-industry business management platform developed by Synchro Tech. The system provides a comprehensive suite of business management tools adaptable to various industries including store management, construction, consultancy, accounting, project management, task management, booking scheduling, and real estate.
The platform is designed with a modular architecture allowing for high reusability, maintainability, and future extensibility across industries. It features responsive design for compatibility with both mobile and desktop browsers.

### 1.1 Project Structure
```
synchro-tech/
├── command-center/
│   ├── parent-frontend/
│   │   ├── public/
│   │   ├── src/
│   │   │   ├── assets/
│   │   │   ├── components/
│   │   │   ├── contexts/
│   │   │   ├── hooks/
│   │   │   ├── layouts/
│   │   │   ├── pages/
│   │   │   ├── services/
│   │   │   ├── state/
│   │   │   ├── styles/
│   │   │   ├── types/
│   │   │   ├── utils/
│   │   │   ├── App.tsx
│   │   │   └── index.tsx
│   │   ├── package.json
│   │   └── README.md
│   └── parent-backend/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/
│       │   │   │   └── com/
│       │   │   │       └── synchrotech/
│       │   │   │           └── commandcenter/
│       │   │   │               ├── CommandCenterApplication.java
│       │   │   │               ├── config/
│       │   │   │               ├── constant/
│       │   │   │               ├── controller/
│       │   │   │               ├── dto/
│       │   │   │               ├── exception/
│       │   │   │               ├── filter/
│       │   │   │               ├── model/
│       │   │   │               ├── repository/
│       │   │   │               ├── security/
│       │   │   │               ├── service/
│       │   │   │               └── util/
│       │   │   └── resources/
│       │   └── test/
│       ├── pom.xml
│       └── README.md
└── README.md
```

#### 1.1.1
Config Package

config/
├── ApplicationConfig.java             - General application configuration
├── AsyncConfig.java                   - Async task executor configuration  
├── CacheConfig.java                   - Redis cache configuration
├── JacksonConfig.java                 - JSON serialization configuration
├── MongoConfig.java                   - MongoDB configuration
├── MultiTenancyConfig.java            - Multi-tenancy routing configuration
├── SecurityConfig.java                - Spring Security configuration
├── SwaggerConfig.java                 - OpenAPI documentation
├── WebConfig.java                     - Web MVC configuration
└── properties/                        - Configuration properties classes
    ├── AppProperties.java
    ├── CacheProperties.java
    ├── MongoProperties.java
    └── SecurityProperties.java

#### 1.1.2
Constant Package

constant/
├── ApiEndpoints.java                  - API endpoint constants
├── CacheConstants.java                - Cache key constants
├── ErrorCodes.java                    - Error code definitions
├── RoleConstants.java                 - Role definitions
├── SecurityConstants.java             - Security-related constants
└── SystemConstants.java               - General system constants
	
#### 1.1.3
Controller Package

controller/
├── AuthController.java                - Authentication endpoints
├── TenantController.java              - Tenant management endpoints
├── UserController.java                - User management endpoints
├── SettingsController.java            - System settings endpoints
├── DepartmentController.java          - Department management
├── RoleController.java                - Role management
├── admin/                             - Admin-specific controllers
│   ├── AdminTenantController.java
│   └── AdminUserController.java
├── core/                              - Core module controllers
│   ├── NotificationController.java
│   ├── AuditLogController.java
│   └── FileStorageController.java
└── module/                            - Industry-specific module controllers
    ├── inventory/
    │   ├── ProductController.java
    │   ├── InventoryController.java
    │   └── SupplierController.java
    ├── customer/
    │   ├── CustomerController.java
    │   └── CustomerGroupController.java
    ├── project/
    │   ├── ProjectController.java
    │   └── TaskController.java
    ├── transaction/
    │   ├── TransactionController.java
    │   └── PaymentController.java
    ├── calendar/
    │   ├── AppointmentController.java
    │   └── ResourceController.java
    └── communication/
        ├── EmailController.java
        └── NotificationTemplateController.java

#### 1.1.4
DTO Package

dto/
├── request/                           - Request DTOs
│   ├── auth/
│   │   ├── LoginRequest.java
│   │   ├── SignupRequest.java
│   │   ├── RefreshTokenRequest.java
│   │   └── PasswordResetRequest.java
│   ├── tenant/
│   │   ├── TenantCreateRequest.java
│   │   └── TenantUpdateRequest.java
│   ├── user/
│   │   ├── UserCreateRequest.java
│   │   └── UserUpdateRequest.java
│   └── module/                        - Module-specific request DTOs
│       ├── product/
│       ├── inventory/
│       ├── project/
│       └── transaction/
├── response/                          - Response DTOs
│   ├── auth/
│   │   ├── JwtResponse.java
│   │   └── TokenRefreshResponse.java
│   ├── ApiResponse.java               - Standard API response wrapper
│   ├── ErrorResponse.java             - Error response structure
│   ├── PagedResponse.java             - Paginated response wrapper
│   └── module/                        - Module-specific response DTOs
│       ├── product/
│       ├── inventory/
│       ├── project/
│       └── transaction/
└── mapper/                            - DTO to Entity mappers
    ├── TenantMapper.java
    ├── UserMapper.java
    ├── RoleMapper.java
    └── module/                        - Module-specific mappers
        ├── ProductMapper.java
        ├── InventoryMapper.java
        ├── ProjectMapper.java
        └── TransactionMapper.java

#### 1.1.4
Exception Package

exception/
├── GlobalExceptionHandler.java        - Central exception handler
├── ApiException.java                  - Base API exception
├── ResourceNotFoundException.java     - Resource not found exception
├── BadRequestException.java           - Invalid request exception
├── UnauthorizedException.java         - Authentication failure exception
├── ForbiddenException.java            - Authorization failure exception
├── TenantException.java               - Tenant-related exceptions
├── ValidationException.java           - Input validation exception
├── ConflictException.java             - Resource conflict exception
└── ServiceException.java              - Service layer exception

#### 1.1.5
Filter Package

filter/
├── JwtAuthenticationFilter.java       - JWT authentication filter
├── TenantContextFilter.java           - Tenant context resolution filter
├── RequestLoggingFilter.java          - Request logging filter
├── CorsFilter.java                    - CORS handling filter
└── SecurityHeadersFilter.java         - Security headers filter

#### 1.1.6
Model Package

model/
├── audit/
│   ├── Auditable.java                 - Base auditing fields
│   ├── AuditLog.java                  - Audit log entity
│   └── EntityAuditListener.java       - JPA audit listener
├── tenant/
│   ├── Tenant.java                    - Tenant entity
│   └── TenantSettings.java            - Tenant settings entity
├── user/
│   ├── User.java                      - User entity
│   ├── Role.java                      - Role entity
│   ├── Permission.java                - Permission entity
│   └── Department.java                - Department entity
├── core/
│   ├── Notification.java              - Notification entity
│   ├── Setting.java                   - Setting entity
│   ├── FileStorage.java               - File storage entity
│   └── Theme.java                     - UI theme entity
└── module/                            - Module-specific entities
    ├── inventory/
    │   ├── Product.java
    │   ├── ProductVariant.java
    │   ├── Inventory.java
    │   ├── Supplier.java
    │   └── Category.java
    ├── customer/
    │   ├── Customer.java
    │   └── CustomerGroup.java
    ├── project/
    │   ├── Project.java
    │   ├── Task.java
    │   ├── Comment.java
    │   └── Attachment.java
    ├── transaction/
    │   ├── Transaction.java
    │   └── PaymentMethod.java
    └── calendar/
        ├── Appointment.java
        ├── Resource.java
        └── Availability.java

#### 1.1.7
Repository Package

repository/
├── tenant/
│   └── TenantRepository.java          - Tenant data access
├── user/
│   ├── UserRepository.java            - User data access
│   ├── RoleRepository.java            - Role data access
│   └── DepartmentRepository.java      - Department data access
├── core/
│   ├── NotificationRepository.java    - Notification data access
│   ├── SettingRepository.java         - Setting data access
│   └── AuditLogRepository.java        - Audit log data access
└── module/                            - Module-specific repositories
    ├── inventory/
    │   ├── ProductRepository.java
    │   ├── InventoryRepository.java
    │   └── SupplierRepository.java
    ├── customer/
    │   ├── CustomerRepository.java
    │   └── CustomerGroupRepository.java
    ├── project/
    │   ├── ProjectRepository.java
    │   └── TaskRepository.java
    ├── transaction/
    │   └── TransactionRepository.java
    └── calendar/
        ├── AppointmentRepository.java
        └── ResourceRepository.java

#### 1.1.8
Security Package

security/
├── JwtTokenProvider.java              - JWT token generation & validation
├── UserDetailsServiceImpl.java        - Custom user details service
├── TenantAuthenticationProvider.java  - Tenant-aware authentication
├── CustomUserDetails.java             - Extended user details
├── PermissionEvaluator.java           - Custom permission evaluator
├── SecurityUtils.java                 - Security utility methods
├── annotation/                        - Custom security annotations
│   ├── CurrentUser.java               - Inject current user
│   ├── IsSuperAdmin.java              - Super admin authorization
│   ├── IsTenantAdmin.java             - Tenant admin authorization
│   └── HasPermission.java             - Permission-based authorization
└── handler/                           - Custom security handlers
    ├── CustomAuthenticationSuccessHandler.java
    ├── CustomAuthenticationFailureHandler.java
    └── CustomAccessDeniedHandler.java

#### 1.1.9
Service Package

service/
├── auth/
│   ├── AuthService.java               - Authentication service interface
│   ├── AuthServiceImpl.java           - Authentication service implementation
│   ├── RefreshTokenService.java       - Refresh token service
│   └── PasswordResetService.java      - Password reset functionality
├── tenant/
│   ├── TenantService.java             - Tenant service interface
│   ├── TenantServiceImpl.java         - Tenant service implementation
│   ├── TenantInitializationService.java - New tenant setup
│   └── TenantContextHolder.java       - Current tenant context
├── user/
│   ├── UserService.java               - User service interface
│   ├── UserServiceImpl.java           - User service implementation
│   ├── RoleService.java               - Role management service interface
│   ├── RoleServiceImpl.java           - Role management implementation
│   └── DepartmentService.java         - Department management service
├── core/
│   ├── NotificationService.java       - Notification service
│   ├── FileStorageService.java        - File storage service
│   ├── AuditService.java              - Audit logging service
│   ├── SettingsService.java           - Settings management service
│   ├── EmailService.java              - Email service
│   └── ExportService.java             - Export to PDF/Excel service
└── module/                            - Module-specific services
    ├── inventory/
    │   ├── ProductService.java
    │   ├── InventoryService.java
    │   └── SupplierService.java
    ├── customer/
    │   ├── CustomerService.java
    │   └── CustomerGroupService.java
    ├── project/
    │   ├── ProjectService.java
    │   └── TaskService.java
    ├── transaction/
    │   ├── TransactionService.java
    │   └── ReportingService.java
    └── calendar/
        ├── AppointmentService.java
        └── ResourceService.java

#### 1.1.10
Util Package

util/
├── DateUtils.java                     - Date manipulation utilities
├── StringUtils.java                   - String manipulation utilities
├── SecurityUtils.java                 - Security-related utilities
├── ValidationUtils.java               - Input validation utilities
├── MongoUtils.java                    - MongoDB helper utilities
├── ExportUtils.java                   - PDF/Excel export utilities
├── FileUtils.java                     - File handling utilities
├── CryptoUtils.java                   - Encryption/decryption utilities
├── JsonUtils.java                     - JSON processing utilities
└── RequestUtils.java                  - HTTP request utilities

#### 1.1.11
Resources

synchro-tech/command-center/parent-backend/src/main/resources/

Configuration Files

├── application.yml                    - Common application properties
├── application-dev.yml                - Development profile properties
├── application-test.yml               - Testing profile properties
├── application-prod.yml               - Production profile properties
├── logback-spring.xml                 - Logging configuration
└── messages/                          - Internationalization
    ├── messages.properties            - Default messages
    ├── messages_en.properties         - English messages
    ├── messages_fr.properties         - French messages
    └── messages_es.properties         - Spanish messages
