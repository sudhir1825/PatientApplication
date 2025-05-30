package com.example.patientApplication.Service;


import com.example.patientApplication.Dto.PatientRegistrationDto;
import com.example.patientApplication.Entity.Patient;

import java.util.Optional;

public interface PatientService {
    Patient registerNewPatient(PatientRegistrationDto dto);
    Optional<Patient> findByContactNumber(String contactNumber);

}

