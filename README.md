# 🎓 PlaceTrack – Placement Management System

A full-stack Placement Management System developed using Spring Boot, Thymeleaf, and MySQL to streamline campus recruitment activities. The application enables students to register, manage profiles, browse job opportunities, and apply for eligible positions, while administrators can manage companies, job postings, and placement processes through a centralized dashboard.

---

## 🚀 Features

### Student Module

* Student Registration and Login
* Profile Management
* Browse Available Jobs
* Eligibility-Based Job Applications
* Application Status Tracking
* Dashboard for Placement Activities

### Admin Module

* Secure Admin Login
* Company Management
* Job Posting and Management
* Student Management
* Application Review and Status Updates
* Placed Students Monitoring

### Eligibility Verification

Students can apply for jobs only if they satisfy:

* Minimum CGPA requirements
* Required branch criteria
* Required skills matching
* Passing year eligibility (if specified)

---

## 🛠 Technology Stack

| Category   | Technology                         |
| ---------- | ---------------------------------- |
| Frontend   | HTML5, CSS3, JavaScript, Thymeleaf |
| Backend    | Spring Boot, Spring MVC            |
| Database   | MySQL                              |
| ORM        | Hibernate, Spring Data JPA         |
| Build Tool | Maven                              |
| Language   | Java 17                            |

---

## 📂 Project Structure

```text
placetrack/
├── src/
│   ├── main/
│   │   ├── java/com/placetrack/
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   ├── templates/
│   │   │   └── application.properties
│   └── pom.xml
```

---

## ⚙️ Installation & Setup

### Prerequisites

* Java 17 or above
* Maven 3.8+
* MySQL 8+

### Step 1: Create Database

```sql
CREATE DATABASE placetrack_db;
```

### Step 2: Configure Database

Update the database configuration in:

```properties
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/placetrack_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### Step 3: Build the Project

```bash
mvn clean install
```

### Step 4: Run the Application

```bash
mvn spring-boot:run
```

### Step 5: Access the Application

```text
http://localhost:8080
```

---

## 🔑 Default Admin Credentials

```text
Username: admin
Password: admin123
```

Note: Default credentials are automatically generated during the first application startup.

---

## 📌 Key Functionalities

* Student Registration & Authentication
* Profile Creation & Management
* Company Management
* Job Posting & Application Management
* Placement Tracking
* Eligibility Validation
* Application Status Updates
* Dashboard Analytics

---

## 🔮 Future Enhancements

* Spring Security Integration
* Password Encryption (BCrypt)
* JWT Authentication
* Resume Upload Functionality
* Email Notifications
* Interview Scheduling Module
* Placement Analytics Dashboard
* Company Recruitment Drive Management

---

## ⚠️ Disclaimer

This project was developed for academic and learning purposes to demonstrate Java Full Stack Development concepts using Spring Boot, Thymeleaf, Hibernate, and MySQL.

---

## 👨‍💻 Developer

**Pradeep Taware**

MCA Student | Java Full Stack Developer

GitHub: https://github.com/pradeeptaware6269

---

## 📄 License

This project is intended for educational and academic purposes only.
