package com.example.patientApplication.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppointmentSlot slot;

    @ManyToOne
    private Patient patient;

    private LocalDateTime bookedAt = LocalDateTime.now();

    // Getters and Setters


    public Appointment() {
    }

    public Appointment(Long id, AppointmentSlot slot, Patient patient, LocalDateTime bookedAt) {
        this.id = id;
        this.slot = slot;
        this.patient = patient;
        this.bookedAt = bookedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppointmentSlot getSlot() {
        return slot;
    }

    public void setSlot(AppointmentSlot slot) {
        this.slot = slot;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
}

