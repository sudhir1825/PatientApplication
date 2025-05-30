package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.DoctorDTO;
import com.example.patientApplication.Dto.MedicineDTO;
import com.example.patientApplication.Entity.Patient;
import com.example.patientApplication.Service.impl.AppointmentSlotServiceImpl;
import com.example.patientApplication.Service.impl.DoctorService;
import com.example.patientApplication.Service.impl.MedicineServiceImpl;
import com.example.patientApplication.Service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentSlotServiceImpl slotService;

    @Autowired
    private MedicineServiceImpl medicineService;

    @Autowired
    private PatientServiceImpl patientService;

    @GetMapping("")
    public String showAdminDashboard() {
        return "admin-dashboard"; // A new Thymeleaf page
    }

    @GetMapping("/doctors")
    public String listDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctor-list";
    }

    @GetMapping("/doctors/new")
    public String showDoctorForm(Model model) {
        model.addAttribute("doctor", new DoctorDTO());
        return "doctor-form";
    }

    @PostMapping("/doctors")
    public String saveDoctor(@ModelAttribute DoctorDTO doctorDTO) {
        doctorService.saveDoctor(doctorDTO);
        return "redirect:/admin/doctors";
    }

    @PostMapping("/slots")
    public String addSlot(@RequestParam Long doctorId,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {
        slotService.addSlot(doctorId, time);
        return "redirect:/admin/doctors";
    }

    // View medicines for a particular patient by contact number
    @GetMapping("/medicines/search")
    public String showSearchForm() {
        return "search-medicine";
    }

    @GetMapping("/medicines")
    public String showMedicinesForPatientGet(@RequestParam String contactNumber, Model model) {
        List<MedicineDTO> medicines = medicineService.getMedicinesByContactNumber(contactNumber);
        model.addAttribute("medicines", medicines);
        model.addAttribute("contactNumber", contactNumber);
        return "medicine-list";
    }

    @PostMapping("/medicines")
    public String showMedicinesForPatient(@RequestParam String contactNumber, Model model) {
        List<MedicineDTO> medicines = medicineService.getMedicinesByContactNumber(contactNumber);
        model.addAttribute("medicines", medicines);        // list of medicines
        model.addAttribute("contactNumber", contactNumber);
        return "medicine-list";
    }

    @GetMapping("/medicines/add")
    public String showAddMedicineForm(@RequestParam String contactNumber, Model model) {
        model.addAttribute("medicine", new MedicineDTO());  // single medicine object for form binding
        model.addAttribute("contactNumber", contactNumber);
        return "medicine-form";
    }

    @PostMapping("/medicines/new")
    public String addMedicine(@ModelAttribute("medicine") MedicineDTO medicineDTO,
                              @RequestParam String contactNumber) {
        medicineDTO.setId(null); // ensure it's a new medicine, no id
        medicineService.addMedicine(medicineDTO);
        return "redirect:/admin/medicines?contactNumber=" + contactNumber;
    }

    @GetMapping("/medicines/edit")
    public String showEditMedicineForm(@RequestParam Long medicineId,
                                       @RequestParam String contactNumber,
                                       Model model) {
        MedicineDTO medicineDTO = medicineService.getMedicineById(medicineId);
        model.addAttribute("medicine", medicineDTO);          // single medicine for editing
        model.addAttribute("contactNumber", contactNumber);
        return "medicine-form";
    }

    @PostMapping("/medicines/update")
    public String updateMedicine(@RequestParam Long medicineId,
                                 @ModelAttribute("medicine") MedicineDTO medicineDTO,
                                 @RequestParam String contactNumber) {
        medicineService.updateMedicine(medicineId, medicineDTO);
        return "redirect:/admin/medicines?contactNumber=" + contactNumber;
    }

    @PostMapping("/medicines/delete")
    public String deleteMedicine(@RequestParam Long medicineId,
                                 @RequestParam String contactNumber) {
        medicineService.deleteMedicine(medicineId);
        return "redirect:/admin/medicines?contactNumber=" + contactNumber;
    }
}


