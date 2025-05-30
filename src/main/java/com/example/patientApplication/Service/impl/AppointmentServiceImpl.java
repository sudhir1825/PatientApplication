package com.example.patientApplication.Service.impl;

import com.example.patientApplication.Dto.AppointmentDTO;
import com.example.patientApplication.Entity.Appointment;
import com.example.patientApplication.Entity.AppointmentSlot;
import com.example.patientApplication.Entity.Patient;
import com.example.patientApplication.Exception.BookingException;
import com.example.patientApplication.Repository.AppointmentRepository;
import com.example.patientApplication.Repository.AppointmentSlotRepository;
import com.example.patientApplication.Repository.PatientRepository;
import com.example.patientApplication.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentSlotRepository slotRepository;

    @Override
    public void bookAppointment(Long slotId, String contactNumber) {
        AppointmentSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new BookingException("Slot not found"));

        if (slot.isBooked()) {
            throw new BookingException("Slot already booked");
        }

        Patient patient = patientRepository.findByContactNumber(contactNumber)
                .orElseThrow(() -> new BookingException("Patient not found"));

        Appointment appointment = new Appointment();
        appointment.setSlot(slot);
        appointment.setPatient(patient);
        appointment.setBookedAt(LocalDateTime.now());

        slot.setBooked(true); // mark as booked
        slotRepository.save(slot);

        appointmentRepository.save(appointment);
    }

    @Override
    public List<AppointmentDTO> getAppointmentsForPatient(String contactNumber) {
        Patient patient = patientRepository.findByContactNumber(contactNumber)
                .orElseThrow(() -> new BookingException("Patient not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        return appointmentRepository.findByPatient(patient).stream()
                .map(appointment -> {
                    AppointmentDTO dto = new AppointmentDTO();
                    dto.setSlotId(appointment.getSlot().getId());
                    dto.setDoctorName(appointment.getSlot().getDoctor().getName());
                    dto.setFormattedSlotTime(appointment.getSlot().getSlotTime().format(formatter));
                    dto.setPatient(appointment.getPatient());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}



