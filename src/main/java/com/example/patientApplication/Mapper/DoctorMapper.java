package com.example.patientApplication.Mapper;

import com.example.patientApplication.Dto.DoctorDTO;
import com.example.patientApplication.Entity.Doctor;

public class DoctorMapper {
    public static DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialization(doctor.getSpecialization());
        return dto;
    }

    public static Doctor toEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        return doctor;
    }
}
