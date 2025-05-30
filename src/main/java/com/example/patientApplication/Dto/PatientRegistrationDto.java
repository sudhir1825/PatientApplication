package com.example.patientApplication.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class PatientRegistrationDto {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Contact number is required")
    @Size(min = 10, max = 10, message = "Must be 10 digits")
    private String contactNumber;

    @NotEmpty(message = "Medical history is required")
    private String medicalHistory;

    @NotEmpty(message = "Password is required")
    private String password;

    public PatientRegistrationDto() {
    }

    public PatientRegistrationDto(String name, String contactNumber, String medicalHistory, String password) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.medicalHistory = medicalHistory;
        this.password = password;
    }



    // Getters and Setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
}


