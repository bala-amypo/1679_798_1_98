// package com.example.demo.model;

// import jakarta.persistence.*;
// import lombok.*;

// import java.time.LocalDateTime;

// @Entity
// @Table(name = "users")
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(unique = true, nullable = false)
//     private String email;

//     @Column(nullable = false)
//     private String password;

//     private String role; // ADMIN, INSTRUCTOR, LEARNER

//     private LocalDateTime createdAt;

//     @PrePersist
//     public void prePersist() {
//         this.createdAt = LocalDateTime.now();
//     }
// }

Project Overview
This is a Spring Boot-based REST API application designed to provide micro-learning content recommendation for learners. The system enables users to register, log in with JWT authentication, instructors to create courses and upload short micro-lessons, and learners to record progress on lessons. The platform includes an intelligent recommendation engine that generates prioritized micro-lesson suggestions based on recent activity, mastery levels, tags, difficulty, and user preferences. The system uses MySQL database and provides comprehensive CRUD operations with JWT-based authentication.
Key Objectives:
Allow users to register and authenticate with JWT tokens
Enable instructors to create courses and micro-lessons
Allow learners to track progress on micro-lessons
Automatically generate personalized lesson recommendations
Maintain audit trails through timestamps
Provide secure access through JWT authentication
Offer RESTful API with Swagger documentation
Key Features:
User registration and authentication with JWT
User role management (LEARNER, INSTRUCTOR, ADMIN)
Course management by instructors
Micro-lesson management with tags, difficulty levels, and content types
Progress tracking with completion percentage and status
Intelligent recommendation engine based on learner behavior
Comprehensive validation and business logic
RESTful API with Swagger UI documentation
Confidence scoring for recommendations
Learner preference tracking (preferred learning style)

Technology Stack
Framework: Spring Boot 3.2.5 (or compatible version)
Java Version: 17 (or higher)
Build Tool: Maven
Database: MySQL 8.0+
ORM: Hibernate/JPA with Lombok
Security: Spring Security with JWT (jjwt version 0.11.5)
API Documentation: Swagger/OpenAPI (springdoc version 2.1.0)
Validation: Jakarta Validation API
Password Encoding: BCrypt
Object Building: Lombok @Builder annotation
Dependency Injection: Spring DI (Constructor Injection required)
Testing Framework: TestNG 7.10.2
Mocking Framework: Mockito
Servlet Support: Jakarta Servlet API

Project Structure
The project follows standard Spring Boot Maven structure with the root package com.example.demo:
src/main/java/com/example/demo/
├── config/
│ ├── SecurityConfig.java
│ ├── ServletConfig.java
│ └── SwaggerConfig.java
├── controller/
│ ├── AuthController.java
│ ├── CourseController.java
│ ├── LessonController.java
│ ├── ProgressController.java
│ └── RecommendationController.java
├── dto/
│ ├── AuthResponse.java
│ ├── RecommendationRequest.java
│ └── (Other DTOs as needed)
├── entity/
│ ├── User.java
│ ├── Course.java
│ ├── MicroLesson.java
│ ├── Progress.java
│ └── Recommendation.java
├── exception/
│ ├── ResourceNotFoundException.java
│ └── GlobalExceptionHandler.java
├── repository/
│ ├── UserRepository.java
│ ├── CourseRepository.java
│ ├── MicroLessonRepository.java
│ ├── ProgressRepository.java
│ └── RecommendationRepository.java
├── security/
│ └── JwtUtil.java
├── service/
│ ├── UserService.java
│ ├── CourseService.java
│ ├── LessonService.java
│ ├── ProgressService.java
│ └── RecommendationService.java
├── service/impl/
│ ├── UserServiceImpl.java
│ ├── CourseServiceImpl.java
│ ├── LessonServiceImpl.java
│ ├── ProgressServiceImpl.java
│ └── RecommendationServiceImpl.java
├── servlet/
│ └── SimpleStatusServlet.java
├── util/
│ └── (Utility classes as needed)
└── DemoApplication.java
src/main/resources/
├── application.properties
└── static/
Critical: All classes must follow the com.example.demo package structure. This is required for automated testing.

Dependencies and Configuration
Maven Dependencies (pom.xml)
The project includes the following key dependencies:
Spring Boot Starter Web: Provides REST API capabilities and embedded Tomcat
Spring Boot Starter Data JPA: JPA and Hibernate support for database operations
MySQL Connector Java: Database driver for MySQL connectivity
Spring Boot Starter Security: Security framework for authentication and authorization
JWT Libraries (jjwt-api, jjwt-impl, jjwt-jackson): JWT token generation and validation (version 0.11.5)
Spring Boot Starter Validation: Jakarta Bean validation support
Springdoc OpenAPI: Swagger/OpenAPI documentation and UI (version 2.1.0)
Lombok: Code generation for builders and annotations
Jakarta Servlet API: For servlet development
TestNG: Testing framework (version 7.10.2)
Mockito: Mocking framework for unit testing
Spring Boot Starter Test: Integration testing support
Build Configuration
Uses Spring Boot Maven Plugin for packaging and running
Maven Surefire Plugin configured for TestNG support
TestNG is the primary testing framework
Build generates executable JAR with embedded Tomcat server

Database Configuration
Application Properties (application.properties)
Database Connection:
URL: jdbc:mysql://localhost:3306/microlearning_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
Username: root
Password: (configure as per environment)
Hibernate DDL Auto: update (automatically creates/updates schema)
Show SQL: true (logs SQL queries for debugging)
Dialect: org.hibernate.dialect.MySQL8Dialect
Server Configuration:
Port: 8080 (configurable)
JWT Configuration:
Secret Key: (use a strong, randomly generated key, minimum 32 characters)
Expiration Time: 86400000 milliseconds (24 hours)

Entity Models
1. User Entity
Package: com.example.demo.entity
Table Name: users
Fields:
id (Long): Primary key, auto-generated using IDENTITY strategy
Annotation: @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
fullName (String): User's full name
Validation: @NotBlank
Max Length: 100 characters
email (String): User's email address
Validation: @Email, @NotBlank
Unique constraint at database level
Annotation: @Column(unique=true)
password (String): Encrypted password (BCrypt encoded)
Validation: @NotBlank
Min Length: 8 characters (validated before encryption)
role (String): User role - "LEARNER", "INSTRUCTOR", or "ADMIN"
Validation: @NotBlank
Default value: "LEARNER"
Allowed values: LEARNER, INSTRUCTOR, ADMIN
preferredLearningStyle (String): Nullable, stores learner preference
Max Length: 50 characters
Optional field
createdAt (LocalDateTime): Timestamp when user was created
Auto-generated via @PrePersist annotation
Annotations:
Must use Lombok @Builder for object creation
Annotation: @Builder, @Data or equivalent
@PrePersist method to set createdAt timestamp
Business Rules:
Email must be unique across all users
Password must be BCrypt encoded before storage
Role defaults to "LEARNER" if not specified
Password must be at least 8 characters
Timestamp automatically set on creation

2. Course Entity
Package: com.example.demo.entity
Table Name: courses
Fields:
id (Long): Primary key, auto-generated using IDENTITY strategy
Annotation: @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
title (String): Course title
Validation: @NotBlank
Max Length: 150 characters
Unique with instructor (composite unique constraint)
description (String): Course description
Max Length: 500 characters
Optional field (nullable)
instructor (User): Many-to-One relationship with User entity
Annotation: @ManyToOne @JoinColumn(name="instructor_id", nullable=false)
Optional: false (required)
Fetch type: LAZY
category (String): Course category
Validation: @NotBlank
Max Length: 50 characters
createdAt (LocalDateTime): Timestamp when course was created
Auto-generated via @PrePersist annotation
Annotations:
Must use Lombok @Builder for object creation
@PrePersist method to set createdAt timestamp
Business Rules:
Title must be unique within the same instructor
Exception message: "Course with this title already exists for this instructor"
Instructor must exist and have role INSTRUCTOR or ADMIN
Timestamp automatically set on creation

3. MicroLesson Entity
Package: com.example.demo.entity
Table Name: micro_lessons
Fields:
id (Long): Primary key, auto-generated using IDENTITY strategy
Annotation: @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
course (Course): Many-to-One relationship with Course entity
Annotation: @ManyToOne @JoinColumn(name="course_id", nullable=false)
Optional: false (required)
Fetch type: LAZY
title (String): Lesson title
Validation: @NotBlank
Max Length: 150 characters
durationMinutes (Integer): Lesson duration in minutes
Validation: @NotNull, @Positive
Max Value: 15 minutes (typical for micro-learning)
contentType (String): Type of content (VIDEO, ARTICLE, QUIZ, INTERACTIVE)
Validation: @NotBlank
Max Length: 50 characters
difficulty (String): Difficulty level (BEGINNER, INTERMEDIATE, ADVANCED)
Validation: @NotBlank
Max Length: 50 characters
tags (String): Comma-separated tags for categorization
Max Length: 500 characters
Optional field
publishDate (LocalDate): Date when lesson was published
Validation: @NotNull
Annotations:
Must use Lombok @Builder for object creation
Business Rules:
Duration must be greater than 0 and typically ≤ 15 minutes
Content type must be from enumerated values
Difficulty must be from enumerated values
Tags help in recommendation engine filtering

4. Progress Entity
Package: com.example.demo.entity
Table Name: progress
Fields:
id (Long): Primary key, auto-generated using IDENTITY strategy
Annotation: @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
user (User): Many-to-One relationship with User entity
Annotation: @ManyToOne @JoinColumn(name="user_id", nullable=false)
Optional: false (required)
Fetch type: LAZY
microLesson (MicroLesson): Many-to-One relationship with MicroLesson entity
Annotation: @ManyToOne @JoinColumn(name="micro_lesson_id", nullable=false)
Optional: false (required)
Fetch type: LAZY
status (String): Progress status (NOT_STARTED, IN_PROGRESS, COMPLETED)
Validation: @NotBlank
Max Length: 20 characters
Default: "NOT_STARTED"
progressPercent (Integer): Completion percentage
Validation: @NotNull, @Min(0), @Max(100)
Valid Range: 0-100
lastAccessedAt (LocalDateTime): Last time the lesson was accessed
Auto-generated via @PrePersist annotation
score (BigDecimal): Optional score for quiz/assessment
Precision: 5, Scale: 2
Nullable field
completedAt (LocalDateTime): Timestamp when lesson was completed
Nullable field
Set when status changes to COMPLETED
Annotations:
Must use Lombok @Builder for object creation
@PrePersist method to set lastAccessedAt timestamp
Business Rules:
Progress percent must be between 0 and 100
If status = COMPLETED then progressPercent must be 100
Only one Progress record per user-lesson combination
Timestamp automatically set/updated on access
Score is optional and used for assessments

5. Recommendation Entity
Package: com.example.demo.entity
Table Name: recommendations
Fields:
id (Long): Primary key, auto-generated using IDENTITY strategy
Annotation: @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
user (User): Many-to-One relationship with User entity
Annotation: @ManyToOne @JoinColumn(name="user_id", nullable=false)
Optional: false (required)
Fetch type: LAZY
generatedAt (LocalDateTime): Timestamp when recommendation was generated
Auto-generated via @PrePersist annotation
recommendedLessonIds (String): Comma-separated IDs of recommended lessons
Validation: @NotBlank
Max Length: 1000 characters
basisSnapshot (String): JSON snapshot of recommendation criteria and data
Max Length: 2000 characters
Nullable field
Stores: user preferences, recent activity, mastery levels, tags, difficulty
confidenceScore (BigDecimal): Confidence score for recommendation
Precision: 3, Scale: 2
Valid Range: 0.0 - 1.0
Validation: @NotNull, @DecimalMin("0.0"), @DecimalMax("1.0")
Annotations:
Must use Lombok @Builder for object creation
@PrePersist method to set generatedAt timestamp
Business Rules:
Timestamp automatically set on creation
Confidence score must be between 0.0 and 1.0
Basis snapshot captures criteria used for recommendation
Multiple recommendations can exist for same user

Repositories
All repositories extend JpaRepository<Entity, Long> providing standard CRUD operations.
1. UserRepository
Package: com.example.demo.repository
Extends: JpaRepository<User, Long>
Custom Methods (Exact Naming Required):
Optional<User> findByEmail(String email): Finds user by email
Inherited Methods:
save(), findById(), findAll(), delete(), count(), etc.

2. CourseRepository
Package: com.example.demo.repository
Extends: JpaRepository<Course, Long>
Custom Methods (Exact Naming Required):
boolean existsByTitleAndInstructorId(String title, Long instructorId): Checks if course with title exists for instructor
List<Course> findByInstructorId(Long instructorId): Finds all courses by instructor
Inherited Methods:
save(), findById(), findAll(), delete(), count(), etc.

3. MicroLessonRepository
Package: com.example.demo.repository
Extends: JpaRepository<MicroLesson, Long>
Custom Methods (Exact Naming Required):
List<MicroLesson> findByFilters(String tags, String difficulty, String contentType): Finds lessons by filter criteria
List<MicroLesson> findByCourseId(Long courseId): Finds all lessons in a course
Inherited Methods:
save(), findById(), findAll(), delete(), count(), etc.

4. ProgressRepository
Package: com.example.demo.repository
Extends: JpaRepository<Progress, Long>
Custom Methods (Exact Naming Required):
Optional<Progress> findByUserIdAndMicroLessonId(Long userId, Long lessonId): Finds progress for specific user-lesson combination
List<Progress> findByUserIdOrderByLastAccessedAtDesc(Long userId): Finds all progress records for user, ordered by recent access
Inherited Methods:
save(), findById(), findAll(), delete(), count(), etc.

5. RecommendationRepository
Package: com.example.demo.repository
Extends: JpaRepository<Recommendation, Long>
Custom Methods (Exact Naming Required):
List<Recommendation> findByUserIdAndGeneratedAtBetween(Long userId, LocalDateTime start, LocalDateTime end): Finds recommendations within date range
List<Recommendation> findByUserIdOrderByGeneratedAtDesc(Long userId): Finds all recommendations for user, ordered by recent generation
Inherited Methods:
save(), findById(), findAll(), delete(), count(), etc.

Service Layer
Service Interfaces and Implementations
1. UserService Interface
Package: com.example.demo.service
Methods:
User register(User user): Registers new user
Validates email uniqueness
BCrypt encodes password
Sets default role to LEARNER if null
AuthResponse login(String email, String password): Authenticates user and returns JWT token
Validates credentials
Returns token and user details
User findById(Long id): Finds user by ID
Throws ResourceNotFoundException if not found
User findByEmail(String email): Finds user by email
Throws ResourceNotFoundException if not found

UserServiceImpl
Package: com.example.demo.service.impl
Annotations: @Service, @Transactional
Constructor Signature (Requires Constructor Injection):
public UserServiceImpl(UserRepository userRepository,
BCryptPasswordEncoder passwordEncoder,
JwtUtil jwtUtil) {
this.userRepository = userRepository;
this.passwordEncoder = passwordEncoder;
this.jwtUtil = jwtUtil;
}
Methods:
register(User user):
Validate email doesn't already exist
Encode password using BCryptPasswordEncoder
Set default role to "LEARNER" if null
Save and return user
login(String email, String password):
Find user by email
Validate password using BCrypt
Generate JWT token with claims (id, email, role)
Return AuthResponse with token and user details
findById(Long id):
Find user by ID
Throw ResourceNotFoundException if not found
findByEmail(String email):
Find user by email
Throw ResourceNotFoundException if not found

2. CourseService Interface
Package: com.example.demo.service
Methods:
Course createCourse(Course course, Long instructorId): Creates new course
Validates instructor exists
Validates title uniqueness per instructor
Course updateCourse(Long courseId, Course course): Updates existing course
List<Course> listCoursesByInstructor(Long instructorId): Retrieves all courses by instructor
Course getCourse(Long courseId): Retrieves course by ID

CourseServiceImpl
Package: com.example.demo.service.impl
Annotations: @Service, @Transactional
Dependencies:
CourseRepository (via constructor)
UserRepository (via constructor)
Methods:
createCourse(Course course, Long instructorId):
Find instructor by ID
Throw ResourceNotFoundException if instructor not found
Validate title doesn't already exist for this instructor
Set instructor on course
Save and return course
updateCourse(Long courseId, Course course):
Find course by ID
Throw ResourceNotFoundException if not found
Update course fields
Save and return updated course
listCoursesByInstructor(Long instructorId):
Return all courses for instructor
getCourse(Long courseId):
Find course by ID
Throw ResourceNotFoundException if not found

3. LessonService Interface
Package: com.example.demo.service
Methods:
MicroLesson addLesson(Long courseId, MicroLesson lesson): Creates new lesson
Validates course exists
Validates duration is positive and ≤ 15 minutes
MicroLesson updateLesson(Long lessonId, MicroLesson lesson): Updates existing lesson
List<MicroLesson> findLessonsByFilters(String tags, String difficulty, String contentType): Searches lessons by criteria
MicroLesson getLesson(Long lessonId): Retrieves lesson by ID

LessonServiceImpl
Package: com.example.demo.service.impl
Annotations: @Service, @Transactional
Dependencies:
MicroLessonRepository (via constructor)
CourseRepository (via constructor)
Methods:
addLesson(Long courseId, MicroLesson lesson):
Find course by ID
Throw ResourceNotFoundException if course not found
Validate duration > 0 and ≤ 15 minutes
Validate contentType and difficulty are valid
Set course on lesson
Save and return lesson
updateLesson(Long lessonId, MicroLesson lesson):
Find lesson by ID
Throw ResourceNotFoundException if not found
Update lesson fields
Save and return updated lesson
findLessonsByFilters(String tags, String difficulty, String contentType):
Call repository findByFilters method
Return filtered lessons
getLesson(Long lessonId):
Find lesson by ID
Throw ResourceNotFoundException if not found

4. ProgressService Interface
Package: com.example.demo.service
Methods:
Progress recordProgress(Long userId, Long lessonId, Progress progress): Records or updates progress
Validates user and lesson exist
Enforces validation rules (percent 0-100, if completed then 100%)
Progress getProgress(Long userId, Long lessonId): Retrieves progress for user-lesson
List<Progress> getUserProgress(Long userId): Retrieves all progress records for user

ProgressServiceImpl
Package: com.example.demo.service.impl
Annotations: @Service, @Transactional
Dependencies:
ProgressRepository (via constructor)
UserRepository (via constructor)
MicroLessonRepository (via constructor)
Methods:
recordProgress(Long userId, Long lessonId, Progress progress):
Find user by ID
Throw ResourceNotFoundException if user not found
Find lesson by ID
Throw ResourceNotFoundException if lesson not found
Validate progressPercent is between 0-100
Validate if status is COMPLETED then progressPercent must be 100
Check if progress record exists
If exists: Update it
If not: Create new one
Save and return progress
getProgress(Long userId, Long lessonId):
Find progress for user-lesson combination
Return progress (may be empty)
getUserProgress(Long userId):
Find all progress records for user, ordered by most recent
Return list of progress

5. RecommendationService Interface
Package: com.example.demo.service
Methods:
Recommendation generateRecommendation(Long userId, RecommendationRequest params): Generates personalized recommendation
Recommendation getLatestRecommendation(Long userId): Retrieves most recent recommendation
List<Recommendation> getRecommendations(Long userId, LocalDate from, LocalDate to): Retrieves recommendations within date range

RecommendationServiceImpl
Package: com.example.demo.service.impl
Annotations: @Service, @Transactional
Dependencies:
RecommendationRepository (via constructor)
ProgressRepository (via constructor)
MicroLessonRepository (via constructor)
UserRepository (via constructor)
Methods:
generateRecommendation(Long userId, RecommendationRequest params):
Find user by ID
Throw ResourceNotFoundException if not found
Analyze user progress and preferences
Get user's recent progress (ordered by access date)
Identify mastery levels
Consider preferred learning style
Apply filtering logic
Consider tags from RecommendationRequest
Consider difficulty levels
Filter out already completed lessons
Score and rank lessons
Calculate confidence score based on criteria match
Consider user preferences
Create Recommendation entity
Set recommended lesson IDs
Set basis snapshot (JSON with criteria)
Set confidence score (0.0-1.0)
Save and return recommendation
getLatestRecommendation(Long userId):
Find latest recommendation for user
Return most recent one (ordered by generatedAt DESC, limit 1)
getRecommendations(Long userId, LocalDate from, LocalDate to):
Convert dates to LocalDateTime (start of day, end of day)
Find all recommendations within date range
Return list of recommendations

Controller Layer
All controllers use Constructor Injection and include Swagger @Tag annotation.
1. AuthController
Package: com.example.demo.controller
Base Path: /auth
Annotations: @RestController, @RequestMapping("/auth"), @Tag(name="Authentication")
Dependencies:
UserService (via constructor)
JwtUtil (via constructor)
Endpoints:
POST /register
Access: Public
Request Body: User object
Response: Created User with HTTP 200
Process: Registers new user with hashed password
POST /login
Access: Public
Request Body: LoginRequest (email, password)
Response: AuthResponse with JWT token and HTTP 200
Process: Authenticates user and returns JWT token

2. CourseController
Package: com.example.demo.controller
Base Path: /courses
Annotations: @RestController, @RequestMapping("/courses"), @Tag(name="Course Management")
Dependencies:
CourseService (via constructor)
Endpoints:
POST /
Access: Protected (INSTRUCTOR/ADMIN)
Request Body: Course object
Request Parameter: instructorId (Long)
Response: Created Course with HTTP 200
PUT /{courseId}
Access: Protected (INSTRUCTOR/ADMIN)
Path Variable: courseId (Long)
Request Body: Course object
Response: Updated Course with HTTP 200
GET /instructor/{instructorId}
Access: Protected
Path Variable: instructorId (Long)
Response: List<Course> with HTTP 200
GET /{courseId}
Access: Protected
Path Variable: courseId (Long)
Response: Course details with HTTP 200

3. LessonController
Package: com.example.demo.controller
Base Path: /lessons
Annotations: @RestController, @RequestMapping("/lessons"), @Tag(name="Lesson Management")
Dependencies:
LessonService (via constructor)
Endpoints:
POST /course/{courseId}
Access: Protected (INSTRUCTOR/ADMIN)
Path Variable: courseId (Long)
Request Body: MicroLesson object
Response: Created MicroLesson with HTTP 200
PUT /{lessonId}
Access: Protected (INSTRUCTOR/ADMIN)
Path Variable: lessonId (Long)
Request Body: MicroLesson object
Response: Updated MicroLesson with HTTP 200
GET /search
Access: Protected
Request Parameters: tags (String), difficulty (String), contentType (String)
Response: List<MicroLesson> with HTTP 200
GET /{lessonId}
Access: Protected
Path Variable: lessonId (Long)
Response: MicroLesson details with HTTP 200

4. ProgressController
Package: com.example.demo.controller
Base Path: /progress
Annotations: @RestController, @RequestMapping("/progress"), @Tag(name="Progress Tracking")
Dependencies:
ProgressService (via constructor)
Endpoints:
POST /{lessonId}
Access: Protected
Path Variable: lessonId (Long)
Request Body: Progress object
Request Parameter: userId (Long)
Response: Updated/Created Progress with HTTP 200
GET /lesson/{lessonId}
Access: Protected
Path Variable: lessonId (Long)
Request Parameter: userId (Long)
Response: Progress for lesson with HTTP 200
GET /user/{userId}
Access: Protected
Path Variable: userId (Long)
Response: List<Progress> with HTTP 200

5. RecommendationController
Package: com.example.demo.controller
Base Path: /recommendations
Annotations: @RestController, @RequestMapping("/recommendations"), @Tag(name="Recommendations")
Dependencies:
RecommendationService (via constructor)
Endpoints:
POST /generate
Access: Protected
Request Parameter: userId (Long)
Request Body: RecommendationRequest object
Response: Generated Recommendation with HTTP 200
GET /latest
Access: Protected
Request Parameter: userId (Long)
Response: Latest Recommendation with HTTP 200
GET /user/{userId}
Access: Protected
Path Variable: userId (Long)
Query Parameters: from (LocalDate), to (LocalDate)
Response: List<Recommendation> with HTTP 200

Security Implementation
SecurityConfig
Package: com.example.demo.config
Annotations: @Configuration, @EnableWebSecurity
Beans:
passwordEncoder(): Returns BCryptPasswordEncoder instance
securityFilterChain(): Configures security filters
Configuration:
JWT-based stateless authentication
/auth/** endpoints are public (no authentication required)
All other endpoints require valid JWT token
Role-based access control (LEARNER, INSTRUCTOR, ADMIN)
CORS configuration if needed
Stateless session management (STATELESS)

JWT Token Management
JwtUtil
Package: com.example.demo.security
Annotations: @Component
Constructor:
public JwtUtil(String secret, Long expirationMs) {
this.secret = secret;
this.expirationMs = expirationMs;
}
Methods:
String generateToken(Map<String, Object> claims, String subject): Creates JWT token
Claims: userId, email, role
Subject: user email
Algorithm: HS256
Expiration: Configurable (default 24 hours)
boolean validateToken(String token): Validates JWT token
Returns true if valid, false otherwise
String getEmailFromToken(String token): Extracts email from token
String getRoleFromToken(String token): Extracts role from token
Long getUserIdFromToken(String token): Extracts user ID from token

Exception Handling
Custom Exceptions
ResourceNotFoundException (com.example.demo.exception)
Extends RuntimeException
Used for missing entities
Error messages:
"User not found"
"Course not found"
"Lesson not found"
"Progress not found"
"Recommendation not found"
"Instructor not found"
GlobalExceptionHandler
Package: com.example.demo.exception
Annotations: @RestControllerAdvice
Handlers:
Handles ResourceNotFoundException (HTTP 404)
Handles validation exceptions (HTTP 400)
Handles authentication exceptions (HTTP 401)
Handles authorization exceptions (HTTP 403)
Handles other exceptions (HTTP 500)

Data Transfer Objects (DTOs)
1. AuthResponse
Package: com.example.demo.dto
Fields:
token (String): JWT token
userId (Long): User ID
email (String): User email
role (String): User role
message (String): Response message

2. RecommendationRequest
Package: com.example.demo.dto
Fields:
tags (String): Comma-separated tags to consider
difficulty (String): Preferred difficulty level
contentType (String): Preferred content type
limit (Integer): Number of recommendations to generate
preferredLearningStyle (String): User's learning style preference

3. LoginRequest
Package: com.example.demo.dto
Fields:
email (String): User email
Validation: @Email, @NotBlank
password (String): User password
Validation: @NotBlank

Utility Classes
Package: com.example.demo.util
Possible Utility Classes:
RecommendationEngine: Encapsulates recommendation generation algorithm
ValidationUtil: Common validation methods
DateUtil: Date/time utilities
JsonUtil: JSON parsing and serialization

Configuration Classes
SwaggerConfig
Package: com.example.demo.config
Annotations: @Configuration
Configuration:
API Title: "Micro-Learning Content Recommendation API"
Description: "REST API for micro-learning content and personalized recommendations"
Version: "1.0.0"
Security: JWT Bearer authentication
Security Scheme Name: "JWT"
Type: HTTP
Scheme: bearer
Bearer Format: JWT
Swagger UI: /swagger-ui/index.html
Security Application:
Applied to all endpoints except /auth/**
JWT Bearer token required for all other endpoints

ServletConfig
Package: com.example.demo.config
Annotations: @Configuration
Beans:
Registers SimpleStatusServlet with path /status-servlet

Servlet Implementation
SimpleStatusServlet
Package: com.example.demo.servlet
Extends: HttpServlet
Methods:
protected void doGet(HttpServletRequest request, HttpServletResponse response):
Sets content type to "text/plain"
Sets response status to 200 OK
Writes "Servlet Alive" message
Handles GET requests to /status-servlet endpoint

API Endpoints
Authentication (Public)
POST /auth/register - Register new user
POST /auth/login - Login and get JWT token
Courses (Protected)
POST /courses - Create course (INSTRUCTOR/ADMIN)
PUT /courses/{courseId} - Update course (INSTRUCTOR/ADMIN)
GET /courses/instructor/{instructorId} - List instructor's courses
GET /courses/{courseId} - Get course details
Lessons (Protected)
POST /lessons/course/{courseId} - Add lesson to course (INSTRUCTOR/ADMIN)
PUT /lessons/{lessonId} - Update lesson (INSTRUCTOR/ADMIN)
GET /lessons/search - Search lessons by filters
GET /lessons/{lessonId} - Get lesson details
Progress (Protected)
POST /progress/{lessonId} - Record/update progress
GET /progress/lesson/{lessonId} - Get user's progress for lesson
GET /progress/user/{userId} - Get user's all progress
Recommendations (Protected)
POST /recommendations/generate - Generate recommendation
GET /recommendations/latest - Get latest recommendation
GET /recommendations/user/{userId} - Get recommendations for user
Servlet
GET /status-servlet - Simple servlet endpoint

Business Logic Details
User Registration Process
User submits registration with email, fullName, password, and optional role
System validates email uniqueness
System BCrypt encodes password
System sets role to "LEARNER" if not provided
System validates password is at least 8 characters
User saved to database with createdAt timestamp
Course Creation Process
Instructor initiates course creation with title, description, and category
System validates instructor exists and has INSTRUCTOR or ADMIN role
System validates title is unique for this instructor
System sets instructor on course
System sets createdAt timestamp
Course persisted to database
Lesson Creation Process
Instructor creates lesson for a course with title, duration, content type, difficulty, tags
System validates course exists
System validates duration > 0 and ≤ 15 minutes
System validates content type is valid (VIDEO, ARTICLE, QUIZ, INTERACTIVE)
System validates difficulty is valid (BEGINNER, INTERMEDIATE, ADVANCED)
System sets course on lesson
Lesson persisted to database
Progress Tracking Process
Learner records progress on a lesson with status and percent
System validates user and lesson exist
System validates progressPercent is 0-100
System validates if status=COMPLETED then progressPercent must be 100
System updates lastAccessedAt timestamp
If status=COMPLETED, set completedAt timestamp
Progress record created or updated in database
Recommendation Generation Process
Learner requests recommendation with optional filters (tags, difficulty, learning style)
System retrieves user's recent progress (sorted by access date)
System analyzes learner's mastery levels and completed lessons
System considers learner's preferred learning style
System filters available lessons based on:
Not already completed
Matches difficulty preference
Matches tags if specified
Matches content type if specified
System scores lessons based on:
Match with user preferences
Match with learning goals
Recommended next topics
System ranks top N lessons (typically 5-10)
System calculates confidence score (0.0-1.0) based on criteria match
System creates basis snapshot (JSON) with recommendation criteria
Recommendation persisted with timestamp and confidence score

Validation Rules
User Registration:
Email: Required, unique, valid format
Password: Required, minimum 8 characters
Full Name: Required, max 100 characters
Role: Optional (defaults to LEARNER)
Course Creation:
Title: Required, unique per instructor, max 150 characters
Category: Required, max 50 characters
Description: Optional, max 500 characters
Instructor: Must exist and have INSTRUCTOR or ADMIN role
Lesson Creation:
Title: Required, max 150 characters
Duration: Required, > 0 and ≤ 15 minutes
Content Type: Required, must be valid enum value
Difficulty: Required, must be valid enum value
Course: Must exist
Publish Date: Required
Tags: Optional, max 500 characters
Progress Recording:
User: Required, must exist
Lesson: Required, must exist
Status: Required, valid values (NOT_STARTED, IN_PROGRESS, COMPLETED)
Progress Percent: Required, 0-100
Validation: If status=COMPLETED then progressPercent must equal 100
Score: Optional, only for assessments
Recommendation Generation:
User: Required, must exist
Tags: Optional, filter criteria
Difficulty: Optional, filter criteria
Content Type: Optional, filter criteria
Confidence Score: Auto-calculated, 0.0-1.0 range

Database Relationships
One-to-Many Relationships
User → Course
One instructor can create many courses
Course table has instructor_id foreign key
Course → MicroLesson
One course can have many lessons
MicroLesson table has course_id foreign key
User → Progress
One user can have many progress records
Progress table has user_id foreign key
MicroLesson → Progress
One lesson can have many progress records
Progress table has micro_lesson_id foreign key
User → Recommendation
One user can have many recommendations
Recommendation table has user_id foreign key
Unique Constraints
users.email: Unique
courses.(title, instructor_id): Unique composite
progress.(user_id, micro_lesson_id): Unique composite
Foreign Keys
courses.instructor_id → users.id
micro_lessons.course_id → courses.id
progress.user_id → users.id
progress.micro_lesson_id → micro_lessons.id
recommendations.user_id → users.id

Recommendation Engine
Algorithm Overview
The recommendation engine analyzes user behavior and preferences to suggest relevant lessons:
Data Inputs:
User's learning profile (role, preferred style, experience level)
User's recent progress (lessons accessed, completion status, scores)
User's learning patterns (frequency, preferred topics, difficulty progression)
Available lessons (tags, difficulty, content type, publication date)
Recommendation filters (user-specified preferences)
Processing Steps:
Data Gathering:
Retrieve user's recent progress (last 30 days)
Calculate mastery levels for each topic/tag
Identify knowledge gaps and next learning topics
Candidate Generation:
Filter lessons not yet completed by user
Apply user-specified filters (tags, difficulty, content type)
Rank by relevance to user's learning path
Scoring Algorithm:
Score = (
preference_match_weight * preference_score +
difficulty_progression_weight * difficulty_score +
topic_relevance_weight * relevance_score +
content_type_preference_weight * content_score
)
Ranking:
Sort candidates by score
Select top N (typically 5-10) recommendations
Calculate confidence score as average of component scores
Basis Snapshot:
Capture criteria used: tags considered, difficulty range, mastery levels
Store user preferences snapshot at time of generation
Record evaluation factors for audit/analysis
Confidence Score Calculation:
Range: 0.0 (no confidence) to 1.0 (high confidence)
Based on: match quality, data completeness, recency of user activity
Formula: Average of individual scoring components with weighting
Example Recommendation:
{
"userId": 1,
"recommendedLessonIds": "5,12,8,15,3",
"confidenceScore": 0.87,
"basisSnapshot": {
"user_mastery": {"Python": 0.75, "Web": 0.60},
"preferred_difficulty": "INTERMEDIATE",
"preferred_content_type": "INTERACTIVE",
"learning_goals": ["Web Development", "Python Advanced"],
"recent_progress": 3,
"avg_completion_rate": 0.82
}
}

Important Implementation Notes
Constructor Injection Requirement
All Service and Controller classes must use Constructor Injection exclusively. Do NOT use Field Injection with @Autowired annotations.
Example:
@Service
public class CourseServiceImpl implements CourseService {
private final CourseRepository courseRepository;
private final UserRepository userRepository;
public CourseServiceImpl(CourseRepository courseRepository, 
                       UserRepository userRepository) {
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
}


}
Lombok Builder Requirement
All Entity classes MUST use Lombok @Builder annotation for object creation via .builder().build() pattern.
Example:
@Entity
@Data
@Builder
public class User {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String fullName;
private String email;
// ... other fields


}
// Usage:
User user = User.builder()
.fullName("John Doe")
.email("john@example.com")
.password(encodedPassword)
.role("LEARNER")
.build();
Password Security
All passwords are BCrypt encoded before storage
Never store raw passwords in database
Minimum 8 characters validation
Password matching uses BCrypt's matches() method
JWT Token Structure
Contains: userId, email, role
24-hour expiration
HS256 signing algorithm
Sent in Authorization header as "Bearer [token]"
Servlet Requirement
The SimpleStatusServlet is required for testing servlet integration:
Must extend HttpServlet
Must override doGet() method
Must return "Servlet Alive" as text/plain
Must respond with HTTP 200 OK
Mapped to /status-servlet path
Timestamp Management
Use @PrePersist annotation for automatic timestamp management:
@PrePersist
protected void onCreate() {
this.createdAt = LocalDateTime.now();
}
@PreUpdate
protected void onUpdate() {
this.updatedAt = LocalDateTime.now();
}
Validation Annotations
Use Jakarta Validation API annotations for input validation:
@NotBlank: String cannot be null or blank
@NotNull: Field cannot be null
@Email: Valid email format
@Positive: Number must be positive
@Min/@Max: Range validation
@DecimalMin/@DecimalMax: Decimal range
Database Schema Notes
Table Naming
Table names use lowercase: users, courses, micro_lessons, progress, recommendations
Column names use lowercase with underscores
ID Generation
All entities use IDENTITY strategy for ID generation
Relies on database auto-increment
Unique Constraints
users.email: Unique
courses.(title, instructor_id): Unique composite
progress.(user_id, micro_lesson_id): Unique composite
Foreign Keys
courses.instructor_id → users.id (cascade delete)
micro_lessons.course_id → courses.id (cascade delete)
progress.user_id → users.id
progress.micro_lesson_id → micro_lessons.id (cascade delete)
recommendations.user_id → users.id

Security Considerations
Password Encoding: All passwords are BCrypt encoded
JWT Tokens: Used for stateless authentication
Input Validation: All inputs validated at entity and DTO levels
SQL Injection Prevention: Uses parameterized queries (JPA)
Role-Based Access: Different endpoints require specific roles
Exception Handling: Structured error responses
CORS Configuration: If API used by frontend, configure CORS
Token Expiration: Tokens expire after 24 hours

API Documentation
Swagger/OpenAPI documentation available at /swagger-ui/index.html
Configured with JWT Bearer token security scheme
Each controller documented with @Tag annotations
Request/response models documented with validation

Application Entry Point
DemoApplication
Package: com.example.demo
Annotations: @SpringBootApplication
Main Method:
Standard Spring Boot application entry point
Runs: SpringApplication.run(DemoApplication.class, args)

Testing Framework
Uses TestNG instead of JUnit
Tests use Mockito for mocking dependencies
Service layer tests verify business logic
Controller tests verify REST endpoints
Repository tests verify data access
Tests validate exception messages and specific error handling
