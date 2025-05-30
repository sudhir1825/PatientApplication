package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.AppointmentDTO;
import com.example.patientApplication.Dto.AppointmentSlotDTO;
import com.example.patientApplication.Service.AppointmentService;
import com.example.patientApplication.Service.AppointmentSlotService;
import com.example.patientApplication.Service.PatientService;
import com.example.patientApplication.Service.impl.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentSlotService slotService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/appointments")
    public String showAppointmentForm(@RequestParam(required = false) Long doctorId,
                                      Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());

        if (doctorId != null) {
            List<AppointmentSlotDTO> availableSlots = slotService.getAvailableSlotsByDoctor(doctorId);
            model.addAttribute("slots", availableSlots);
            model.addAttribute("selectedDoctorId", doctorId);
        }

        model.addAttribute("appointment", new AppointmentDTO());
        return "appointments";
    }

    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute("appointment") AppointmentDTO dto,
                                  Authentication authentication,
                                  Model model) {
        String contactNumber = authentication.getName();
        appointmentService.bookAppointment(dto.getSlotId(), contactNumber);
        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String showConfirmation() {
        return "appointment-confirmation";
    }

    @GetMapping("/appointments/slots")
    @ResponseBody
    public List<AppointmentSlotDTO> getSlotsByDoctor(@RequestParam Long doctorId) {
        return slotService.getAvailableSlotsByDoctor(doctorId);
    }
}
