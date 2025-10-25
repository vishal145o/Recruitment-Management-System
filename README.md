# Recruitment Management System - Minimal Runnable Skeleton

This repository is a simplified, runnable skeleton derived from the uploaded `java-spring-boot-guide.pdf`.
It uses H2 in-memory database so the application can be run without external configuration.

## Run

Prerequisites: JDK 17, Maven

Build and run:

```bash
mvn clean package
mvn spring-boot:run
```

Server starts at: http://localhost:8080/api

## Endpoints

- POST /api/auth/signup  -> JSON body: { "name": "...", "email": "...", "password": "..." }
- POST /api/auth/login   -> JSON body: { "email": "...", "password": "..." }
- POST /api/admin/job    -> Create job (no auth in this skeleton)
- GET  /api/admin/jobs   -> List jobs
- POST /api/uploadResume -> multipart form field `resume`


