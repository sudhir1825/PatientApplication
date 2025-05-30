package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.AppointmentDTO;
import com.example.patientApplication.Dto.MedicineDTO;
import com.example.patientApplication.Entity.Appointment;
import com.example.patientApplication.Entity.Patient;
import com.example.patientApplication.Service.AppointmentService;
import com.example.patientApplication.Service.MedicineService;
import com.example.patientApplication.Service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private PatientServiceImpl patientService;
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MedicineService medicineService;
    @GetMapping("/")
    public String home() {
        return "redirect:/home"; // Returns home.html
    }

    @GetMapping("/home")
    public String showHomePage(Authentication authentication, Model model) {
        // Get contact number from logged-in user
        String contactNumber = authentication.getName();


        // Fetch appointments by contact number
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsForPatient(contactNumber);
        model.addAttribute("appointments", appointments);

        List<MedicineDTO> medicines = medicineService.getMedicinesByContactNumber(contactNumber);
        model.addAttribute("medicines", medicines);

        return "home";  //Thymeleaf home.html template
    }

}
