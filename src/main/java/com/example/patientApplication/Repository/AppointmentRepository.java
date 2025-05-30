package com.example.patientApplication.Repository;

import com.example.patientApplication.Entity.Appointment;
import com.example.patientApplication.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(Patient patient);
}

