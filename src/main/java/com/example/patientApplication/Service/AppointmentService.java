package com.example.patientApplication.Service;

import com.example.patientApplication.Dto.AppointmentDTO;
import com.example.patientApplication.Entity.Appointment;

import java.util.List;

public interface AppointmentService {
    void bookAppointment(Long slotId, String contactNumber);
    List<AppointmentDTO> getAppointmentsForPatient(String contactNumber);

}


