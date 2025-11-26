# CLAUDE.md - BizConnect Backend Development Guide

**Last Updated:** 2025-11-26
**Project Version:** 0.0.1-SNAPSHOT
**Current Development Phase:** Entity Model Definition (Pre-Service Layer)

## Table of Contents
1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Project Structure](#project-structure)
4. [Development Environment Setup](#development-environment-setup)
5. [Architecture & Design Patterns](#architecture--design-patterns)
6. [Entity Model & Database](#entity-model--database)
7. [Coding Conventions](#coding-conventions)
8. [Development Workflow](#development-workflow)
9. [Common Tasks](#common-tasks)
10. [Testing Guidelines](#testing-guidelines)
11. [Important Files Reference](#important-files-reference)

---

## Project Overview

**BizConnect** is a business networking platform backend built with Spring Boot. The system facilitates:
- User and company management
- Product catalog and ordering
- Real-time messaging (chatrooms)
- Social networking (friendships)
- Inquiry/support ticketing
- Scheduling and notifications

**Current Implementation Status:**
- ✅ Complete entity model (29 entities)
- ✅ Docker containerization
- ✅ Database configuration (MariaDB)
- ✅ Swagger/OpenAPI setup
- ⏳ Pending: Repository layer
- ⏳ Pending: Service layer
- ⏳ Pending: REST Controllers
- ⏳ Pending: DTOs and validation
- ⏳ Pending: Authentication/Authorization

---

## Technology Stack

### Core Framework
- **Java:** 21 (LTS)
- **Spring Boot:** 3.5.6
- **Build Tool:** Gradle 8.5 with wrapper
- **Package Manager:** Maven Central

### Key Dependencies
```gradle
// Data Persistence
spring-boot-starter-data-jpa      // JPA/Hibernate ORM
spring-boot-starter-jdbc          // JDBC support
mariadb-java-client              // MariaDB driver

// Web Framework
spring-boot-starter-web          // REST API support

// Documentation
springdoc-openapi-starter-webmvc-ui:2.8.13  // Swagger UI

// Development Tools
lombok                           // Boilerplate reduction

// Testing
spring-boot-starter-test        // Spring Test framework
junit-platform-launcher         // JUnit 5
```

### Database
- **RDBMS:** MariaDB 10.11
- **Hibernate Dialect:** MariaDBDialect
- **Schema Management:** Auto-update (DDL auto)

### Containerization
- **Docker:** Multi-stage build
- **Docker Compose:** Version 3.8
- **Network:** Custom bridge network (`bizconnect-network`)

---

## Project Structure

```
BizConnect-BE/
├── src/
│   ├── main/
│   │   ├── java/com/springboot/bizconnect/
│   │   │   ├── config/
│   │   │   │   └── SwaggerConfig.java           # OpenAPI configuration
│   │   │   ├── entity/
│   │   │   │   ├── BaseEntity.java              # Audit fields (createdAt, updatedAt)
│   │   │   │   ├── user.java                    # User management
│   │   │   │   ├── company.java                 # Company management
│   │   │   │   ├── product.java                 # Product catalog
│   │   │   │   ├── order.java & order_item.java # Order system
│   │   │   │   ├── chatroom.java & message.java # Messaging
│   │   │   │   ├── inquiry.java                 # Support tickets
│   │   │   │   └── ... (29 entities total)
│   │   │   ├── enums/
│   │   │   │   ├── orderStatus.java             # ACTIVE, ANSWERED, CLOSED
│   │   │   │   ├── inquiryStatus.java           # ACTIVE, INACTIVE, SUSPENDED
│   │   │   │   └── friendStatus.java            # PENDING, ACCEPTED, REJECTED
│   │   │   └── BizConnectApplication.java       # Main application entry
│   │   └── resources/
│   │       └── application.properties           # Spring configuration
│   └── test/
│       └── java/com/springboot/bizconnect/
│           └── BizConnectApplicationTests.java  # Context load test
├── gradle/                                       # Gradle wrapper
├── Dockerfile                                    # Multi-stage Docker build
├── docker-compose.yml                            # Container orchestration
├── build.gradle                                  # Build configuration
├── settings.gradle                               # Project settings
├── .gitignore                                    # Git exclusions
└── README.md                                     # Project documentation
```

### Package Organization

**Base Package:** `com.springboot.bizconnect`

| Package | Purpose | Current Status |
|---------|---------|----------------|
| `config` | Spring Bean configurations | Swagger only |
| `entity` | JPA entity classes | ✅ Complete (29 entities) |
| `enums` | Enumeration types | ✅ Complete (3 enums) |
| `repository` | Spring Data JPA repositories | ⏳ Not implemented |
| `service` | Business logic layer | ⏳ Not implemented |
| `controller` | REST API endpoints | ⏳ Not implemented |
| `dto` | Data Transfer Objects | ⏳ Not implemented |
| `exception` | Custom exceptions | ⏳ Not implemented |

---

## Development Environment Setup

### Prerequisites
- Java 21 JDK
- Docker & Docker Compose
- Gradle 8.5+ (or use wrapper)
- IDE with Lombok support (IntelliJ IDEA recommended)

### Local Development Setup

**1. Clone and Navigate:**
```bash
git clone <repository-url>
cd BizConnect-BE
```

**2. Run with Docker Compose (Recommended):**
```bash
docker-compose up --build
```

This will:
- Start MariaDB container on port 3306
- Build and start Spring Boot app on port 8301
- Configure networking and persistence

**3. Access Points:**
- Application: `http://localhost:8301`
- Swagger UI: `http://localhost:8301/swagger-ui.html` (once controllers are added)
- OpenAPI JSON: `http://localhost:8301/v3/api-docs`

**4. Database Access:**
```bash
# Connect to MariaDB container
docker exec -it bizconnect-mariadb mysql -u lsh -plsh bizconnect

# Or via host
mysql -h localhost -P 3306 -u lsh -plsh bizconnect
```

### Manual Build (Without Docker)

**1. Ensure MariaDB is running:**
```bash
# Update application.properties with your DB credentials
spring.datasource.url=jdbc:mariadb://localhost:3306/bizconnect
spring.datasource.username=<your-user>
spring.datasource.password=<your-password>
```

**2. Build and Run:**
```bash
# Build
./gradlew build

# Run (skip tests)
./gradlew bootRun

# Or run tests then execute
./gradlew test
java -jar build/libs/bizconnect-0.0.1-SNAPSHOT.jar
```

### Environment Variables

All environment variables have sensible defaults for local development:

| Variable | Default | Description |
|----------|---------|-------------|
| `SERVER_PORT` | 8301 | Application HTTP port |
| `SPRING_PROFILE` | dev | Active Spring profile |
| `DB_HOST` | localhost | Database host |
| `DB_PORT` | 3306 | Database port |
| `DB_NAME` | bizconnect | Database name |
| `DB_USERNAME` | lsh | Database user |
| `DB_PASSWORD` | lsh | Database password |

---

## Architecture & Design Patterns

### Layered Architecture (Planned)

```
┌─────────────────────────────────────┐
│         Controller Layer            │  ← REST endpoints
│  (UserController, ProductController)│
└──────────────┬──────────────────────┘
               │ DTOs
┌──────────────▼──────────────────────┐
│          Service Layer              │  ← Business logic
│   (UserService, ProductService)     │
└──────────────┬──────────────────────┘
               │ Entities
┌──────────────▼──────────────────────┐
│        Repository Layer             │  ← Data access
│  (UserRepository, ProductRepository)│
└──────────────┬──────────────────────┘
               │ JPA/Hibernate
┌──────────────▼──────────────────────┐
│           Database                  │  ← MariaDB
│            (MariaDB)                │
└─────────────────────────────────────┘
```

### Key Design Patterns

#### 1. **Base Entity Pattern (Inheritance)**
All timestamped entities extend `BaseEntity`:

```java
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
```

**Usage:** Entities like `user`, `company`, `product`, `order`, `inquiry`, `schedule`, `notice`, `friend_request` extend this.

**Benefits:**
- Automatic timestamp management
- DRY (Don't Repeat Yourself)
- Consistent audit trail across entities

#### 2. **Lazy Loading Strategy**
All foreign key relationships use `FetchType.LAZY`:

```java
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "role_no")
private role role;
```

**Why:**
- Prevents N+1 query problems
- Reduces memory footprint
- Improves initial load performance

**Important:** When implementing services, explicitly fetch related entities when needed using JOIN FETCH or EntityGraph.

#### 3. **Composite Key Pattern (Embedded IDs)**
Used for many-to-many association tables:

```java
// Example: company_product entity
@EmbeddedId
private CompanyProductNo companyProductNo;

// Embedded key class
@Embeddable
public static class CompanyProductNo implements Serializable {
    @Column(name = "company_no")
    private Long companyNo;

    @Column(name = "product_no")
    private Long productNo;
}
```

**Used in:**
- `company_product` (companyNo + productNo)
- `chat_join` (userNo + chatroomNo)

#### 4. **Soft Delete Pattern**
Many entities use `isDeleted` flag instead of hard deletes:

```java
@Column(name = "is_deleted", nullable = false)
private Boolean isDeleted = false;
```

**Entities using soft delete:**
- `user`, `company`, `product`, `schedule`, `notice`, `alarm`, `inquiry_attachment`

**Implementation guidance:**
- Default to `false`
- In repositories, filter by `isDeleted = false` in queries
- Never physically DELETE rows for these entities
- Consider scheduled cleanup jobs for old soft-deleted data

#### 5. **Enumeration Strategy**
Enums are stored as strings for readability:

```java
@Enumerated(EnumType.STRING)
@Column(name = "status")
private orderStatus status;
```

**Available Enums:**
- `orderStatus`: ACTIVE, ANSWERED, CLOSED
- `inquiryStatus`: ACTIVE, INACTIVE, SUSPENDED
- `friendStatus`: PENDING, ACCEPTED, REJECTED

**When to add enums:**
- Fixed set of values that rarely change
- Values need validation and type safety
- Prefer over magic strings/numbers

---

## Entity Model & Database

### Entity Domains & Relationships

#### Domain 1: User Management
```
user (PK: userNo)
  ├── OneToOne → role (roleNo)
  ├── OneToOne → position (positionNo)
  ├── OneToOne → user_status (userStatusNo)
  ├── OneToOne → company (companyNo)
  └── OneToMany → alarm (alarmNo)
```

**Related Entities:**
- `user` - Core user (name, email, password, phoneNumber, address, imageUrl)
- `role` - User role lookup
- `position` - Job position lookup
- `user_status` - Status lookup (active, inactive, etc.)

#### Domain 2: Company/Organization
```
company (PK: companyNo)
  ├── OneToOne → affiliation (affiliationNo)
  ├── OneToOne → branch (branchNo)
  ├── OneToOne → business_registration (businessRegistrationNo)
  └── OneToOne → corporate_account (corporateAccountNo)
```

**Related Entities:**
- `company` - Company details (phoneNumber, address)
- `affiliation` - Organizational affiliation
- `branch` - Branch/location
- `business_registration` - Business reg number
- `corporate_account` - Corporate bank account

#### Domain 3: Product Catalog
```
product (PK: productNo)
  ├── OneToOne → unit (unitNo)
  ├── OneToOne → category (categoryNo)
  ├── OneToOne → manufacturer (manufacturerNo)
  └── OneToOne → product_status (productStatusNo)

company_product (Composite: companyNo + productNo)
  ├── ManyToOne → company
  └── ManyToOne → product
```

**Related Entities:**
- `product` - Product (name, content, price, imageUrl)
- `category` - Product category
- `manufacturer` - Manufacturer/brand
- `unit` - Unit of measure (kg, liter, box)
- `product_status` - Availability status
- `company_product` - Company-Product association

#### Domain 4: Order Management
```
order (PK: orderNo)
  ├── ManyToOne → user (userNo)
  ├── ManyToOne → company (companyNo)
  └── Enum: orderStatus (ACTIVE, ANSWERED, CLOSED)

order_item (PK: orderItemNo)
  ├── ManyToOne → order (orderNo)
  └── ManyToOne → product (productNo)
```

#### Domain 5: Messaging
```
chatroom (PK: chatroomNo)
  └── Enum: orderStatus

chat_join (Composite: userNo + chatroomNo)
  ├── ManyToOne → user
  └── ManyToOne → chatroom

message (PK: messageNo)
  ├── ManyToOne → user (userNo)
  └── ManyToOne → chatroom (chatroomNo)
```

#### Domain 6: Social/Friendship
```
friendship (PK: friendshipNo)
  ├── ManyToOne → user (userNo)
  └── ManyToOne → user as friend (userNo)

friend_request (PK: friendRequestNo)
  ├── ManyToOne → user as sender (userNo)
  ├── ManyToOne → user as receiver (userNo)
  └── Enum: friendStatus (PENDING, ACCEPTED, REJECTED)
```

#### Domain 7: Support/Inquiry
```
inquiry (PK: inquiryNo)
  ├── ManyToOne → company (companyNo)
  ├── ManyToOne → user (userNo)
  ├── OneToOne → inquiry_category (inquiryCategoryNo)
  └── Enum: inquiryStatus (ACTIVE, INACTIVE, SUSPENDED)

inquiry_attachment (PK: inquiryAttachmentNo)
  └── OneToOne → inquiry (inquiryNo)
```

#### Domain 8: Content
```
notice (PK: noticeNo)
  └── ManyToOne → user (userNo)

schedule (PK: scheduleNo)
  ├── ManyToOne → company (companyNo)
  └── ManyToOne → user (userNo)

alarm (PK: alarmNo)
  └── ManyToOne → user (userNo)
```

### Database Schema Conventions

**Primary Keys:**
- Naming: `{entity}_no` (e.g., `user_no`, `product_no`)
- Type: `Long` or `Integer` based on expected scale
- Strategy: `@GeneratedValue(strategy = GenerationType.IDENTITY)` (auto-increment)

**Foreign Keys:**
- Naming: Same as referenced primary key (e.g., `user_no`, `company_no`)
- Always use `@JoinColumn(name = "...")` to specify column name
- Always `FetchType.LAZY` for performance

**Timestamps:**
- Pattern: `{action}_at` (e.g., `created_at`, `updated_at`, `started_at`, `ended_at`)
- Type: `LocalDateTime`
- Automatic via `@CreatedDate` / `@LastModifiedDate` in `BaseEntity`

**Booleans:**
- Pattern: `is_{descriptor}` (e.g., `is_deleted`, `is_open`, `is_used`, `is_read`)
- Type: `Boolean`
- Always provide default value

**Text Fields:**
- Short text: `VARCHAR(n)` via `@Column(length = n)`
- Long text: `TEXT` via `@Column(columnDefinition = "TEXT")`

### JPA Configuration

**application.properties:**
```properties
# Hibernate DDL
spring.jpa.hibernate.ddl-auto=update  # Auto-update schema (use 'validate' in production)

# SQL Logging
spring.jpa.show-sql=true              # Print SQL statements
spring.jpa.properties.hibernate.format_sql=true  # Format SQL for readability

# Dialect
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
```

**Important:**
- `ddl-auto=update` is safe for development but risky in production
- For production, use `validate` and manage schema via Flyway/Liquibase
- Enable SQL logging helps debugging but impacts performance

---

## Coding Conventions

### Naming Conventions

#### Java Classes & Interfaces
**IMPORTANT DEVIATION:** This project uses **lowercase snake_case** for entity class names, which violates standard Java conventions:

```java
// Current (non-standard but consistent in this project)
public class user extends BaseEntity { }
public class company_product { }

// Standard Java (PascalCase) - NOT used here
public class User extends BaseEntity { }
public class CompanyProduct { }
```

**When adding new entities:**
- Follow the existing pattern: lowercase with underscores
- Keep consistency with the current codebase
- Entity class name matches database table name exactly

#### Variables & Methods
- **camelCase** for variables: `userId`, `companyName`, `isDeleted`
- **camelCase** for methods: `getUserById()`, `createOrder()`
- **UPPER_SNAKE_CASE** for constants: `MAX_RETRY_COUNT`, `DEFAULT_PAGE_SIZE`

#### Database
- **snake_case** for table names: `user`, `company_product`, `order_item`
- **snake_case** for column names: `user_no`, `created_at`, `image_url`
- **Suffix patterns:**
  - `_no` for primary/foreign keys
  - `_at` for timestamps
  - `_url` for URLs
  - `is_` for booleans

### Lombok Annotations

Use these consistently across all new code:

```java
@Getter                    // Generate all getters
@Setter                    // Generate all setters
@NoArgsConstructor        // Generate no-arg constructor (required by JPA)
@AllArgsConstructor       // Generate constructor with all fields
@Builder                  // Generate builder pattern
public class ExampleEntity extends BaseEntity {
    // fields
}
```

**Best Practices:**
- Always include `@NoArgsConstructor` for JPA entities
- Use `@Builder` for test data creation
- Avoid `@Data` (too broad, includes equals/hashCode which can cause issues with JPA)

### Entity Annotations

Standard pattern for entities:

```java
@Entity
@Table(name = "entity_name")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class entity_name extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_name_no")
    private Long entityNameNo;

    @Column(name = "field_name", nullable = false, length = 255)
    private String fieldName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_entity_no")
    private related_entity relatedEntity;
}
```

### Code Organization

**File-level ordering:**
1. Class annotations
2. Primary key field
3. Simple fields (String, Integer, Boolean, etc.)
4. Enum fields
5. Relationship fields (OneToOne, ManyToOne, OneToMany)
6. Nested classes (Embeddable IDs)

**Example:**
```java
@Entity
@Table(name = "user")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class user extends BaseEntity {

    // 1. Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    // 2. Simple fields
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    // 3. Booleans
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    // 4. Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_no")
    private role role;

    @OneToMany(mappedBy = "user")
    private List<alarm> alarms = new ArrayList<>();
}
```

---

## Development Workflow

### Git Branching Strategy

**Main Branch:** `main` (or `master`)
- Production-ready code
- Protected branch
- Requires PR and review

**Feature Branches:**
- Pattern: `claude/claude-md-{session-id}`
- Auto-generated for AI assistant sessions
- Example: `claude/claude-md-mig09arp90yb42zw-0149u1DYZzHgepLsdCdJfhyo`

**When developing:**
```bash
# Always verify current branch
git branch

# Develop on the designated feature branch
git checkout claude/claude-md-{session-id}

# Commit frequently with clear messages
git add .
git commit -m "Implement UserRepository with custom queries"

# Push to remote
git push -u origin claude/claude-md-{session-id}
```

### Commit Message Convention

Use descriptive commit messages in this format:

```
[Component] Brief description

Examples:
✅ [Entity] Add inventory tracking fields to product
✅ [Repository] Implement UserRepository with email lookup
✅ [Service] Add order creation business logic
✅ [Controller] Implement user registration endpoint
✅ [Config] Add JWT authentication configuration
✅ [Test] Add unit tests for ProductService
✅ [Docker] Update docker-compose with Redis cache
✅ [Fix] Resolve N+1 query issue in order fetching
```

**Components:**
- Entity, Repository, Service, Controller, Config, Test, Docker, Fix, Refactor, Docs

### Pull Request Workflow

**When creating PRs:**
1. Ensure all tests pass
2. Include clear description of changes
3. Reference related issues if any
4. Update CLAUDE.md if architecture changes

**PR Title Format:**
```
[Component] Brief description of changes

Examples:
[Repository] Implement all repository interfaces
[Service] Add user authentication service
[API] Implement product CRUD endpoints
```

### Recent Commit History
```
2bce125 [이성훈] Docker Compose 설정
71bd838 Docker Compose 설정 추가
31c46b8 Docker 환경 구성 및 컨테이너화 완료
3f3125f [이성훈] Entity, Swagger 초기 설정
64a227f entity, swaggerconfig 초기 설정
```

---

## Common Tasks

### Task 1: Add a New Entity

**Steps:**
1. Create entity class in `src/main/java/com/springboot/bizconnect/entity/`
2. Follow naming convention (lowercase snake_case)
3. Extend `BaseEntity` if timestamps are needed
4. Add Lombok annotations
5. Define fields with proper JPA annotations
6. Define relationships with `FetchType.LAZY`

**Example:**
```java
package com.springboot.bizconnect.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "new_entity")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class new_entity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "new_entity_no")
    private Long newEntityNo;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
```

### Task 2: Create Repository Interface

**Location:** `src/main/java/com/springboot/bizconnect/repository/`

**Example:**
```java
package com.springboot.bizconnect.repository;

import com.springboot.bizconnect.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<user, Long> {

    // Spring Data JPA auto-generates implementation
    Optional<user> findByEmail(String email);

    List<user> findByIsDeletedFalse();

    // Custom query
    @Query("SELECT u FROM user u WHERE u.isDeleted = false AND u.company.companyNo = :companyNo")
    List<user> findActiveUsersByCompany(Long companyNo);
}
```

### Task 3: Implement Service Layer

**Location:** `src/main/java/com/springboot/bizconnect/service/`

**Example:**
```java
package com.springboot.bizconnect.service;

import com.springboot.bizconnect.entity.user;
import com.springboot.bizconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public user getUserById(Long userNo) {
        return userRepository.findById(userNo)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public user createUser(user newUser) {
        return userRepository.save(newUser);
    }

    public List<user> getActiveUsers() {
        return userRepository.findByIsDeletedFalse();
    }
}
```

### Task 4: Create REST Controller

**Location:** `src/main/java/com/springboot/bizconnect/controller/`

**Example:**
```java
package com.springboot.bizconnect.controller;

import com.springboot.bizconnect.entity.user;
import com.springboot.bizconnect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userNo}")
    public ResponseEntity<user> getUser(@PathVariable Long userNo) {
        return ResponseEntity.ok(userService.getUserById(userNo));
    }

    @GetMapping
    public ResponseEntity<List<user>> getActiveUsers() {
        return ResponseEntity.ok(userService.getActiveUsers());
    }

    @PostMapping
    public ResponseEntity<user> createUser(@RequestBody user newUser) {
        return ResponseEntity.ok(userService.createUser(newUser));
    }
}
```

### Task 5: Run Application Locally

**With Docker Compose (Recommended):**
```bash
# Start all services
docker-compose up --build

# Run in detached mode
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop all services
docker-compose down

# Remove volumes (clean database)
docker-compose down -v
```

**Without Docker:**
```bash
# Build
./gradlew clean build

# Run
./gradlew bootRun

# Or run JAR
java -jar build/libs/bizconnect-0.0.1-SNAPSHOT.jar
```

### Task 6: Database Operations

**Access MariaDB:**
```bash
# Via Docker container
docker exec -it bizconnect-mariadb mysql -u lsh -plsh bizconnect

# Via host (if running locally)
mysql -h localhost -P 3306 -u lsh -plsh bizconnect
```

**Useful SQL Queries:**
```sql
-- Show all tables
SHOW TABLES;

-- Describe entity structure
DESCRIBE user;

-- View data
SELECT * FROM user WHERE is_deleted = false;

-- Check entity relationships
SELECT u.name, r.name as role, c.company_no
FROM user u
LEFT JOIN role r ON u.role_no = r.role_no
LEFT JOIN company c ON u.company_no = c.company_no;
```

### Task 7: Add API Documentation

Swagger is already configured. When you add controllers, they'll automatically appear in Swagger UI.

**Enhance with annotations:**
```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for user operations")
public class UserController {

    @Operation(summary = "Get user by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userNo}")
    public ResponseEntity<user> getUser(@PathVariable Long userNo) {
        // ...
    }
}
```

---

## Testing Guidelines

### Current Test Setup
- **Framework:** JUnit 5 Platform
- **Spring Support:** Spring Boot Test
- **Test Location:** `src/test/java/com/springboot/bizconnect/`

### Testing Strategy

**Test Pyramid:**
```
       /\
      /  \    E2E Tests (Few)
     /----\
    /      \  Integration Tests (Some)
   /--------\
  /          \ Unit Tests (Many)
 /____________\
```

### Unit Test Example (Service Layer)

```java
package com.springboot.bizconnect.service;

import com.springboot.bizconnect.entity.user;
import com.springboot.bizconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserById_WhenUserExists_ReturnsUser() {
        // Given
        Long userNo = 1L;
        user expectedUser = user.builder()
            .userNo(userNo)
            .name("Test User")
            .email("test@example.com")
            .build();

        when(userRepository.findById(userNo)).thenReturn(Optional.of(expectedUser));

        // When
        user actualUser = userService.getUserById(userNo);

        // Then
        assertNotNull(actualUser);
        assertEquals(expectedUser.getUserNo(), actualUser.getUserNo());
        verify(userRepository, times(1)).findById(userNo);
    }

    @Test
    void getUserById_WhenUserNotFound_ThrowsException() {
        // Given
        Long userNo = 999L;
        when(userRepository.findById(userNo)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> userService.getUserById(userNo));
    }
}
```

### Integration Test Example (Repository Layer)

```java
package com.springboot.bizconnect.repository;

import com.springboot.bizconnect.entity.user;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByEmail_WhenEmailExists_ReturnsUser() {
        // Given
        user testUser = user.builder()
            .name("Test User")
            .email("test@example.com")
            .password("password123")
            .isDeleted(false)
            .build();
        entityManager.persistAndFlush(testUser);

        // When
        Optional<user> foundUser = userRepository.findByEmail("test@example.com");

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals("Test User", foundUser.get().getName());
    }

    @Test
    void findByIsDeletedFalse_ReturnsOnlyActiveUsers() {
        // Given
        user activeUser = user.builder()
            .name("Active")
            .email("active@example.com")
            .isDeleted(false)
            .build();
        user deletedUser = user.builder()
            .name("Deleted")
            .email("deleted@example.com")
            .isDeleted(true)
            .build();
        entityManager.persist(activeUser);
        entityManager.persist(deletedUser);
        entityManager.flush();

        // When
        var activeUsers = userRepository.findByIsDeletedFalse();

        // Then
        assertEquals(1, activeUsers.size());
        assertEquals("Active", activeUsers.get(0).getName());
    }
}
```

### Running Tests

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests UserServiceTest

# Run with coverage (if configured)
./gradlew test jacocoTestReport

# Skip tests during build
./gradlew build -x test
```

---

## Important Files Reference

### Configuration Files

| File | Purpose | Key Settings |
|------|---------|--------------|
| `build.gradle` | Gradle build configuration | Dependencies, Java 21, Spring Boot 3.5.6 |
| `application.properties` | Spring configuration | Database connection, JPA settings, port |
| `docker-compose.yml` | Container orchestration | MariaDB + App services, networking |
| `Dockerfile` | Docker image build | Multi-stage build, Java 21 runtime |
| `.gitignore` | Git exclusions | Build artifacts, IDE files |

### Source Code Files

| Location | Description |
|----------|-------------|
| `src/main/java/com/springboot/bizconnect/BizConnectApplication.java` | Application entry point with `@EnableJpaAuditing` |
| `src/main/java/com/springboot/bizconnect/config/SwaggerConfig.java` | OpenAPI 3.0 configuration |
| `src/main/java/com/springboot/bizconnect/entity/BaseEntity.java` | Abstract base for audit fields |
| `src/main/java/com/springboot/bizconnect/entity/*.java` | 28 JPA entity classes |
| `src/main/java/com/springboot/bizconnect/enums/*.java` | 3 enumeration types |

### Quick File Lookup

**Find entity files:**
```bash
find src/main/java -name "*.java" -path "*/entity/*"
```

**Find all Lombok usages:**
```bash
grep -r "@Getter" src/main/java
```

**Check entity relationships:**
```bash
grep -r "FetchType.LAZY" src/main/java/com/springboot/bizconnect/entity/
```

---

## Key Principles for AI Assistants

### DO:
✅ Follow existing naming conventions (lowercase entity names)
✅ Always extend `BaseEntity` for entities with timestamps
✅ Use `FetchType.LAZY` for all relationships
✅ Add `@NoArgsConstructor` to all JPA entities
✅ Implement soft delete with `isDeleted` flag
✅ Write descriptive commit messages with component tags
✅ Add unit tests for new services
✅ Use Lombok annotations to reduce boilerplate
✅ Follow the established package structure
✅ Check for existing entities before creating new ones

### DON'T:
❌ Change entity naming convention to PascalCase
❌ Use `FetchType.EAGER` (causes N+1 problems)
❌ Hard delete entities that have `isDeleted` field
❌ Skip `@NoArgsConstructor` in entities (JPA requires it)
❌ Add unnecessary abstractions in early development
❌ Change database credentials in version control
❌ Skip tests when implementing business logic
❌ Mix different architectural patterns
❌ Add features beyond current requirements
❌ Ignore the existing domain organization

### When in Doubt:
1. **Check existing entities** for patterns
2. **Consult this CLAUDE.md** for conventions
3. **Follow Spring Boot best practices** for new features
4. **Ask for clarification** on ambiguous requirements
5. **Run tests** before committing changes

---

## Troubleshooting

### Common Issues

**Issue:** "Table doesn't exist" error
```
Solution: Check spring.jpa.hibernate.ddl-auto=update in application.properties
Verify database connection is working
```

**Issue:** LazyInitializationException
```
Solution: Use @Transactional on service methods
Or fetch associations explicitly with JOIN FETCH
```

**Issue:** Docker build fails
```
Solution: Ensure Dockerfile has correct Java version (21)
Check docker-compose.yml environment variables
Run: docker-compose down -v && docker-compose up --build
```

**Issue:** Tests failing with database errors
```
Solution: Add @DataJpaTest for repository tests
Use TestEntityManager for test data setup
Check test database configuration
```

---

## Next Steps & Roadmap

### Immediate Next Steps (Priority Order)

1. **Repository Layer** ⏳
   - Create Spring Data JPA repositories for all entities
   - Add custom query methods where needed
   - Implement pagination and sorting

2. **Service Layer** ⏳
   - Implement business logic for each domain
   - Add transaction management
   - Implement soft delete filtering

3. **DTO Layer** ⏳
   - Create request/response DTOs
   - Add validation annotations
   - Implement DTO mappers (ModelMapper or MapStruct)

4. **Controller Layer** ⏳
   - Implement REST endpoints
   - Add Swagger documentation
   - Implement error handling

5. **Security** ⏳
   - Add Spring Security
   - Implement JWT authentication
   - Add authorization rules

6. **Validation & Error Handling** ⏳
   - Add @Valid annotations
   - Create custom exceptions
   - Implement global exception handler

7. **Testing** ⏳
   - Unit tests for services
   - Integration tests for repositories
   - API tests for controllers

### Future Enhancements

- Database migration with Flyway/Liquibase
- Redis caching layer
- Async processing with @Async
- WebSocket for real-time chat
- File upload/download for attachments
- Email notifications
- API versioning
- Rate limiting
- API logging and monitoring

---

**Document Maintained By:** AI Assistants
**Review Frequency:** After major architectural changes
**Last Reviewed:** 2025-11-26
