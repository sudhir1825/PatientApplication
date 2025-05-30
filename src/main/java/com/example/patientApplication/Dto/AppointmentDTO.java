package com.example.patientApplication.Dto;

import com.example.patientApplication.Entity.Patient;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Long slotId;
    private String doctorName;
    private LocalDateTime appointmentTime;

    private Patient patient;

    private String formattedSlotTime;

    public String getFormattedSlotTime() {
        return formattedSlotTime;
    }

    public void setFormattedSlotTime(String formattedSlotTime) {
        this.formattedSlotTime = formattedSlotTime;
    }
    public AppointmentDTO() {
    }

    public AppointmentDTO(Long slotId, String doctorName, LocalDateTime appointmentTime, Patient patient) {
        this.slotId = slotId;
        this.doctorName = doctorName;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
    }

    // Getters and Setters
    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}


