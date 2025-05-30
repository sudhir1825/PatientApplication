package com.example.patientApplication.Service.impl;


import com.example.patientApplication.Dto.PatientRegistrationDto;
import com.example.patientApplication.Entity.Patient;
import com.example.patientApplication.Repository.PatientRepository;
import com.example.patientApplication.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Patient registerNewPatient(PatientRegistrationDto dto) {
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setContactNumber(dto.getContactNumber());
        patient.setMedicalHistory(dto.getMedicalHistory());
        patient.setPassword(passwordEncoder.encode(dto.getPassword()));
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> findByContactNumber(String contactNumber) {
        return patientRepository.findByContactNumber(contactNumber);
    }


}

