package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.AppointmentDTO;
import com.example.patientApplication.Dto.AppointmentSlotDTO;
import com.example.patientApplication.Service.AppointmentService;
import com.example.patientApplication.Service.AppointmentSlotService;
import com.example.patientApplication.Service.PatientService;
import com.example.patientApplication.Service.impl.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments") // New base path to avoid collision
public class AppointmentRestController {

    @Autowired
    private AppointmentSlotService slotService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    // Get all doctors
    @GetMapping("/doctors")
    public List<?> getAllDoctors() {
        return doctorService.getAllDoctors(); // You can cast this to List<DoctorDTO> if needed
    }

    // Get available slots for a doctor
    @GetMapping("/slots")
    public List<AppointmentSlotDTO> getSlotsByDoctor(@RequestParam Long doctorId) {
        return slotService.getAvailableSlotsByDoctor(doctorId);
    }

    // Book appointment
    @PostMapping("/book")
    public String bookAppointment(@RequestBody AppointmentDTO dto, Authentication authentication) {
        String contactNumber = authentication.getName();
        appointmentService.bookAppointment(dto.getSlotId(), contactNumber);
        return "Appointment booked successfully";
    }

    // Dummy confirmation response
    @GetMapping("/confirmation")
    public String showConfirmation() {
        return "Appointment confirmed";
    }
}
