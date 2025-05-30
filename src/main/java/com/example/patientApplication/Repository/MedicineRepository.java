package com.example.patientApplication.Repository;

import com.example.patientApplication.Entity.Medicine;
import com.example.patientApplication.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByPatient(Patient patient);
}
