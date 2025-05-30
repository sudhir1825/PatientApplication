package com.example.patientApplication.Mapper;

import com.example.patientApplication.Dto.AppointmentSlotDTO;
import com.example.patientApplication.Entity.AppointmentSlot;

public class AppointmentSlotMapper {
    public static AppointmentSlotDTO toDTO(AppointmentSlot slot) {
        AppointmentSlotDTO dto = new AppointmentSlotDTO();
        dto.setId(slot.getId());
        dto.setSlotTime(slot.getSlotTime());
        dto.setBooked(slot.isBooked());
        dto.setDoctorId(slot.getDoctor().getId());
        return dto;
    }
}
