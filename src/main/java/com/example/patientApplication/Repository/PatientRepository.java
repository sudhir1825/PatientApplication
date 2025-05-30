package com.example.patientApplication.Repository;

import com.example.patientApplication.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, String> {
    Optional<Patient> findByContactNumber(String contactNumber);

}

