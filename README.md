🚀 Library Management System – Spring Boot Backend Project

Library Management System is a Spring Boot backend application designed to manage books, users, subscriptions, loans, payments, fines, reservations, and reviews.  
The project follows a RESTful API architecture with secure authentication and role-based access control.

✨ Features

✔ Secure JWT-based Authentication & Authorization  
✔ Role-Based Access Control (Admin / User)  
✔ Book Management & Genre Management  
✔ Book Loan & Return Workflow  
✔ Reservation System  
✔ Subscription & Subscription Plans  
✔ Fine Calculation & Tracking  
✔ Payment Processing Integration  
✔ Book Reviews & Wishlist  
✔ Global Exception Handling  
✔ Clean Layered Architecture  

🖥️ Tech Stack

Backend  
- Java  
- Spring Boot  
- Spring Security  
- Spring Data JPA  
- REST APIs  

Security  
- JWT Authentication  
- Role-Based Authorization  

Database  
- MySQL  

Payment & Utilities  
- Razorpay Integration  
- Email Service  
- DTO & Mapper Pattern  

API Testing  
- Postman  

📂 Project Structure

Library_Management_System_APP/
├── configurations/
├── controller/
├── service/
├── repository/
├── entity/
├── payload/
├── mapper/
├── domain/
├── event/
└── exception/

⚙️ Application Workflow

User Authentication → JWT Token Generation → Secure API Access → Business Logic Processing → Database Operations → API Responses

⚙️ Setup Instructions

1️⃣ Clone Repository  
git clone https://github.com/Pinkuu108/Library_Management_System_APP.git

2️⃣ Open Project  
Import into IntelliJ IDEA / Eclipse / Spring Tool Suite

3️⃣ Configure Database (application.properties)

spring.datasource.url=jdbc:mysql://localhost:3306/your_database  
spring.datasource.username=your_username  
spring.datasource.password=your_password  

4️⃣ Configure JWT & Security (if needed)

jwt.secret=your_secret_key  

5️⃣ Configure Payment Gateway (Optional)

Razorpay API Keys

6️⃣ Run Application  
Run the Spring Boot main class

🎯 Key Learning Highlights

✔ Spring Security & JWT Authentication  
✔ REST API Design  
✔ Clean Layered Architecture  
✔ DTO & Mapper Pattern  
✔ Exception Handling Strategy  
✔ Payment Gateway Integration  
✔ Enterprise Backend Concepts  

👨‍💻 Author

Pinku Prusty  
Java Developer  

🔗 LinkedIn: https://www.linkedin.com/in/pinkuna-prusty-55b487273/  
📧 Email: pinkunaprusty108@gmail.com
