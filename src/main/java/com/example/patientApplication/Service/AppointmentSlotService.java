package com.example.patientApplication.Service;

import com.example.patientApplication.Dto.AppointmentSlotDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentSlotService {
    void addSlot(Long doctorId, LocalDateTime time);
    List<AppointmentSlotDTO> getAvailableSlotsByDoctor(Long doctorId);
    AppointmentSlotDTO getSlotById(Long id);
}
