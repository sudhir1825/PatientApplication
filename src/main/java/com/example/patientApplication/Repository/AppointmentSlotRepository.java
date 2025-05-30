package com.example.patientApplication.Repository;

import com.example.patientApplication.Entity.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Long> {
    List<AppointmentSlot> findByDoctorIdAndBookedFalse(Long doctorId);
}

