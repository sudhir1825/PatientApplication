package com.example.patientApplication.Service.impl;

import com.example.patientApplication.Dto.AppointmentSlotDTO;
import com.example.patientApplication.Entity.AppointmentSlot;
import com.example.patientApplication.Entity.Doctor;
import com.example.patientApplication.Repository.AppointmentSlotRepository;
import com.example.patientApplication.Repository.DoctorRepository;
import com.example.patientApplication.Service.AppointmentSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentSlotServiceImpl implements AppointmentSlotService {

    @Autowired
    private AppointmentSlotRepository slotRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void addSlot(Long doctorId, LocalDateTime time) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isPresent()) {
            AppointmentSlot slot = new AppointmentSlot();
            slot.setDoctor(doctorOpt.get());
            slot.setSlotTime(time);
            slot.setBooked(false);
            slotRepository.save(slot);
        } else {
            throw new IllegalArgumentException("Doctor not found with ID: " + doctorId);
        }
    }

    @Override
    public List<AppointmentSlotDTO> getAvailableSlotsByDoctor(Long doctorId) {
        return slotRepository.findByDoctorIdAndBookedFalse(doctorId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentSlotDTO getSlotById(Long id) {
        AppointmentSlot slot = slotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        return mapToDTO(slot);
    }

    private AppointmentSlotDTO mapToDTO(AppointmentSlot slot) {
        AppointmentSlotDTO dto = new AppointmentSlotDTO();
        dto.setId(slot.getId());
        dto.setSlotTime(slot.getSlotTime());
        dto.setDoctorId(slot.getDoctor().getId());
        return dto;
    }
}




