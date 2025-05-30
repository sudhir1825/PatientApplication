package com.example.patientApplication.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Patient {

    @Id
    @Column(length = 10)
    private String contactNumber;  // Used as login ID

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Medical history is required")
    private String medicalHistory;

    private String password;

    private String role = "PATIENT";

    public Patient() {
    }

    public Patient(String contactNumber, String name, String medicalHistory, String password, String role) {
        this.contactNumber = contactNumber;
        this.name = name;
        this.medicalHistory = medicalHistory;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

