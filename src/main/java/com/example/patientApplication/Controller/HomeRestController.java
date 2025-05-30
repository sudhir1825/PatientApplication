package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.AppointmentDTO;
import com.example.patientApplication.Dto.MedicineDTO;
import com.example.patientApplication.Service.AppointmentService;
import com.example.patientApplication.Service.MedicineService;
import com.example.patientApplication.Service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeRestController {

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MedicineService medicineService;

    // Endpoint to get appointments and medicines of the logged-in patient
    @GetMapping
    public HomeResponse getHomeData(Authentication authentication) {
        String contactNumber = authentication.getName();

        List<AppointmentDTO> appointments = appointmentService.getAppointmentsForPatient(contactNumber);
        List<MedicineDTO> medicines = medicineService.getMedicinesByContactNumber(contactNumber);

        return new HomeResponse(appointments, medicines);
    }

    // Response DTO class wrapping appointments and medicines
    public static class HomeResponse {
        private List<AppointmentDTO> appointments;
        private List<MedicineDTO> medicines;

        public HomeResponse(List<AppointmentDTO> appointments, List<MedicineDTO> medicines) {
            this.appointments = appointments;
            this.medicines = medicines;
        }

        public List<AppointmentDTO> getAppointments() {
            return appointments;
        }

        public List<MedicineDTO> getMedicines() {
            return medicines;
        }
    }
}
