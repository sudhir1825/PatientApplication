package com.example.patientApplication.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AppointmentSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime slotTime;

    private boolean booked;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public AppointmentSlot() {
    }

    public AppointmentSlot(Long id, LocalDateTime slotTime, boolean booked, Doctor doctor) {
        this.id = id;
        this.slotTime = slotTime;
        this.booked = booked;
        this.doctor = doctor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(LocalDateTime slotTime) {
        this.slotTime = slotTime;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


}
