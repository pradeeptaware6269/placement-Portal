# 🎓 PlaceTrack – Placement Management System

A simple, clean, full-stack Placement Management System built with **Spring Boot**, **Thymeleaf**, and **MySQL**.

---

## ⚙️ Technology Stack

| Layer      | Technology                    |
|------------|-------------------------------|
| Frontend   | HTML, CSS, JavaScript (Thymeleaf templates) |
| Backend    | Spring Boot 3.2, Spring MVC   |
| ORM        | Spring Data JPA + Hibernate   |
| Database   | MySQL 8+                      |
| Build Tool | Maven                         |
| Java       | Java 17                       |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8+

### 1. Create Database
```sql
CREATE DATABASE placetrack_db;
```
> Tables are auto-created by Hibernate (`ddl-auto=update`)

### 2. Configure Database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/placetrack_db?...
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 3. Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Access Application
Open: http://localhost:8080

---

## 🔐 Default Admin Credentials
```
Username: admin
Password: admin123
```
Auto-created on first startup.

---

## 📌 Application URL Map

| URL                        | Description              |
|----------------------------|--------------------------|
| `/`                        | Landing Page             |
| `/student/register`        | Student Registration     |
| `/student/login`           | Student Login            |
| `/student/dashboard`       | Student Dashboard        |
| `/student/profile`         | Edit Profile             |
| `/student/jobs`            | Browse & Apply for Jobs  |
| `/admin/login`             | Admin Login              |
| `/admin/dashboard`         | Admin Dashboard          |
| `/admin/companies`         | Manage Companies         |
| `/admin/companies/add`     | Add Company              |
| `/admin/jobs`              | View All Jobs            |
| `/admin/jobs/post`         | Post New Job             |
| `/admin/applications`      | Manage Applications      |
| `/admin/students`          | View All Students        |
| `/admin/placed-students`   | View Placed Students     |

---

## 🧩 Module Overview

### Student Module
- Register / Login
- Complete profile (CGPA, skills, branch)
- Browse open jobs with eligibility check
- Apply for eligible jobs only
- Track application status

### Admin Module
- Login
- Add / delete companies
- Post jobs with eligibility criteria
- View all applications
- Update application status (Applied → Shortlisted → Rejected / Selected)
- View placed students

### Eligibility Check Logic
Before a student can apply:
1. ✅ CGPA ≥ Job's minimum CGPA
2. ✅ Student branch is in job's required branches (or job allows ALL)
3. ✅ At least one skill matches required skills
4. ✅ Passing year matches (if specified)

---

## 📂 Project Structure

```
placetrack/
├── src/main/java/com/placetrack/
│   ├── PlaceTrackApplication.java
│   ├── config/
│   │   └── DataInitializer.java
│   ├── controller/
│   │   ├── HomeController.java
│   │   ├── StudentController.java
│   │   └── AdminController.java
│   ├── model/
│   │   ├── Student.java
│   │   ├── Admin.java
│   │   ├── Company.java
│   │   ├── Job.java
│   │   └── Application.java
│   ├── repository/
│   │   ├── StudentRepository.java
│   │   ├── AdminRepository.java
│   │   ├── CompanyRepository.java
│   │   ├── JobRepository.java
│   │   └── ApplicationRepository.java
│   └── service/
│       ├── StudentService.java
│       ├── AdminService.java
│       ├── CompanyService.java
│       ├── JobService.java
│       └── ApplicationService.java
├── src/main/resources/
│   ├── application.properties
│   ├── static/
│   │   ├── css/style.css
│   │   └── js/main.js
│   └── templates/
│       ├── index.html
│       ├── student/
│       │   ├── register.html
│       │   ├── login.html
│       │   ├── dashboard.html
│       │   ├── profile.html
│       │   └── view-jobs.html
│       └── admin/
│           ├── login.html
│           ├── dashboard.html
│           ├── companies.html
│           ├── add-company.html
│           ├── jobs.html
│           ├── post-job.html
│           ├── applications.html
│           ├── students.html
│           └── placed-students.html
└── pom.xml
```

---

## ⚠️ Limitations (Academic Project)
- Plain-text password storage (no encryption)
- No Spring Security / JWT
- No resume upload
- No email notifications
- Simple session-based auth (HttpSession)

---

## 📝 License
Academic / Educational use only.
