package com.example.patientApplication.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;

    // One doctor can have many appointment slots
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<AppointmentSlot> slots = new ArrayList<>();


    public Doctor() {
    }

    public Doctor(Long id, String name, String specialization, List<AppointmentSlot> slots) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.slots = slots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public List<AppointmentSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<AppointmentSlot> slots) {
        this.slots = slots;
    }
}
