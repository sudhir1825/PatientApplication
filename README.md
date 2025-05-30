# Patient Medicine and Appointment System

A full-stack Spring Boot web application for managing patient registrations, booking appointments with doctors, and handling medication details. It supports both admin and patient functionalities with login, CRUD operations, and secure access.

How the application works:

Check it out in this link - https://drive.google.com/file/d/1X6-ry6Bs7qq2QDmn7-1mxXSXefMoSY72/view?usp=sharing


---

## 📝 Description

This system allows patients to:
- Register and log in using a contact number.
- Book appointments with doctors.
- View their booked appointments and medications.

Admins can:
- Add/edit/delete doctors and appointment slots.
- Manage patient medications based on contact number.
- View registered patients and appointment data.

---

## 🛠 Tech Stack

### Backend
- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Thymeleaf
- Maven

### Frontend
- Thymeleaf templates
- Bootstrap 5
- HTML/CSS

### Testing
- JUnit 5
- Mockito

---

## 🚀 Features

### ✅ Patient Side
- Register and log in via contact number
- Book appointments from available slots
- View medicines prescribed by the admin

### 🛡 Admin Side
- Admin login with secured credentials
- Add/Edit/Delete doctors
- Add/Edit/Delete appointment slots
- Manage medications per patient (based on contact number)
- Admin dashboard with buttons for Doctors & Medicines views

---

## 📁 Project Structure


```bash
patientApplication/
├── docs/
│   └── PatientApp.json
├── src/
│   ├── main/
│   │   ├── java/com/example/patientApplication/
│   │   │   ├── PatientApplication.java
│   │   │   ├── config/
│   │   │   │   ├── CustomAuthenticationEntryPoint.java
│   │   │   │   ├── CustomAuthenticationSuccessHandler.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── Controller/
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── AdminRestController.java
│   │   │   │   ├── AppointmentController.java
│   │   │   │   ├── AppointmentRestController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── AuthRestController.java
│   │   │   │   ├── CustomErrorController.java
│   │   │   │   ├── HomeController.java
│   │   │   │   └── HomeRestController.java
│   │   │   ├── Dto/
│   │   │   │   ├── AppointmentDTO.java
│   │   │   │   ├── AppointmentSlotDTO.java
│   │   │   │   ├── DoctorDTO.java
│   │   │   │   ├── MedicineDTO.java
│   │   │   │   └── PatientRegistrationDto.java
│   │   │   ├── Entity/
│   │   │   │   ├── Appointment.java
│   │   │   │   ├── AppointmentSlot.java
│   │   │   │   ├── Doctor.java
│   │   │   │   ├── Medicine.java
│   │   │   │   └── Patient.java
│   │   │   ├── Exception/
│   │   │   │   ├── BookingException.java
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── Mapper/
│   │   │   │   ├── AppointmentSlotMapper.java
│   │   │   │   └── DoctorMapper.java
│   │   │   ├── Repository/
│   │   │   │   ├── AppointmentRepository.java
│   │   │   │   ├── AppointmentSlotRepository.java
│   │   │   │   ├── DoctorRepository.java
│   │   │   │   ├── MedicineRepository.java
│   │   │   │   └── PatientRepository.java
│   │   │   ├── Service/
│   │   │   │   ├── impl/
│   │   │   │   │   ├── AppointmentServiceImpl.java
│   │   │   │   │   ├── AppointmentSlotServiceImpl.java
│   │   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   │   ├── DoctorService.java
│   │   │   │   │   ├── MedicineServiceImpl.java
│   │   │   │   │   └── PatientServiceImpl.java
│   │   │   │   ├── AppointmentService.java
│   │   │   │   ├── AppointmentSlotService.java
│   │   │   │   ├── MedicineService.java
│   │   │   │   └── PatientService.java
│   ├── resources/
│   │   ├── static/
│   │   ├── templates/
│   │   │   ├── admin-dashboard.html
│   │   │   ├── appointment-confirmation.html
│   │   │   ├── appointments.html
│   │   │   ├── doctor-form.html
│   │   │   ├── doctor-list.html
│   │   │   ├── error.html
│   │   │   ├── home.html
│   │   │   ├── login.html
│   │   │   ├── medicine-form.html
│   │   │   ├── medicine-list.html
│   │   │   ├── register.html
│   │   │   └── search-medicine.html
│   │   └── application.properties
│   └── test/java/com/example/patientApplication/
│       ├── Controller/
│       │   ├── AdminControllerTest.java
│       │   ├── AppointmentControllerTest.java
│       │   ├── AuthControllerTest.java
│       │   └── HomeControllerTest.java
│       ├── Service/
│       │   ├── AppointmentSlotServiceImplTest.java
│       │   ├── DoctorServiceTest.java
│       │   ├── MedicineServiceImplTest.java
│       │   └── PatientServiceImplTest.java
│       └── PatientApplicationTests.java
├── .gitattributes
└── .gitignore
```

---

## 🧪 Testing

- Unit tests available for controller and service layers
- Run with:
```bash
./mvnw test
```
🖥 How to Run

Clone the Repository
```bash
git clone https://github.com/yourusername/patientApplication.git
cd patientApplication
```
Configure the Database
Update src/main/resources/application.properties with your MySQL DB credentials:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/patient_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```
Build and Run the Project
```bash
./mvnw spring-boot:run
```
Access the Application

Patient Login: http://localhost:8080/login

Admin Dashboard: http://localhost:8080/admin/dashboard

📌 Notes

Default roles assumed are ROLE_PATIENT and ROLE_ADMIN

Uses BCrypt password encoding

Custom login and access denied pages included

Sudhir-R
