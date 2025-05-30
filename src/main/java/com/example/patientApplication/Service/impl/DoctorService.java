package com.example.patientApplication.Service.impl;

import com.example.patientApplication.Dto.DoctorDTO;
import com.example.patientApplication.Entity.Doctor;
import com.example.patientApplication.Mapper.DoctorMapper;
import com.example.patientApplication.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    public void saveDoctor(DoctorDTO dto) {
        Doctor doctor = DoctorMapper.toEntity(dto);
        doctorRepo.save(doctor);
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepo.findAll().stream()
                .map(DoctorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
    }
}

