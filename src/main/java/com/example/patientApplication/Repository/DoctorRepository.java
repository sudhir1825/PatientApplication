package com.example.patientApplication.Repository;

import com.example.patientApplication.Entity.AppointmentSlot;
import com.example.patientApplication.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {}


