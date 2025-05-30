package com.example.patientApplication.Dto;

import java.time.LocalDateTime;

public class AppointmentSlotDTO {
    private Long id;
    private LocalDateTime slotTime;
    private boolean booked;
    private Long doctorId;

    // Getters and Setters


    public AppointmentSlotDTO() {
    }

    public AppointmentSlotDTO(Long id, LocalDateTime slotTime, boolean booked, Long doctorId) {
        this.id = id;
        this.slotTime = slotTime;
        this.booked = booked;
        this.doctorId = doctorId;
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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}

