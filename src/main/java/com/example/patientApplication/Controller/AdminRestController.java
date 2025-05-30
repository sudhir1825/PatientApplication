package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.DoctorDTO;
import com.example.patientApplication.Dto.MedicineDTO;
import com.example.patientApplication.Service.impl.AppointmentSlotServiceImpl;
import com.example.patientApplication.Service.impl.DoctorService;
import com.example.patientApplication.Service.impl.MedicineServiceImpl;
import com.example.patientApplication.Service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentSlotServiceImpl slotService;

    @Autowired
    private MedicineServiceImpl medicineService;

    @Autowired
    private PatientServiceImpl patientService;

    // Get list of doctors
    @GetMapping("/doctors")
    public List<DoctorDTO> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // Add a new doctor
    @PostMapping("/doctors")
    public String addDoctor(@RequestBody DoctorDTO doctorDTO) {
        doctorService.saveDoctor(doctorDTO);
        return "Doctor added successfully";
    }

    // Add a slot for a doctor
    @PostMapping("/slots")
    public String addSlot(@RequestParam Long doctorId,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {
        slotService.addSlot(doctorId, time);
        return "Slot added successfully";
    }

    // Get all medicines by patient contact number
    @GetMapping("/medicines")
    public List<MedicineDTO> getMedicinesByContactNumber(@RequestParam String contactNumber) {
        return medicineService.getMedicinesByContactNumber(contactNumber);
    }

    // Add new medicine
    @PostMapping("/medicines")
    public String addMedicine(@RequestBody MedicineDTO medicineDTO,
                              @RequestParam String contactNumber) {
        medicineDTO.setId(null); // ensure it's a new record
        medicineService.addMedicine(medicineDTO);
        return "Medicine added successfully";
    }

    // Update existing medicine
    @PutMapping("/medicines/{medicineId}")
    public String updateMedicine(@PathVariable Long medicineId,
                                 @RequestBody MedicineDTO medicineDTO,
                                 @RequestParam String contactNumber) {
        medicineService.updateMedicine(medicineId, medicineDTO);
        return "Medicine updated successfully";
    }

    // Delete a medicine
    @DeleteMapping("/medicines/{medicineId}")
    public String deleteMedicine(@PathVariable Long medicineId,
                                 @RequestParam String contactNumber) {
        medicineService.deleteMedicine(medicineId);
        return "Medicine deleted successfully";
    }

    // Get a single medicine by ID (for edit support)
    @GetMapping("/medicines/{medicineId}")
    public MedicineDTO getMedicineById(@PathVariable Long medicineId) {
        return medicineService.getMedicineById(medicineId);
    }
}
