package com.example.patientApplication.Service.impl;

import com.example.patientApplication.Dto.MedicineDTO;
import com.example.patientApplication.Entity.Medicine;
import com.example.patientApplication.Entity.Patient;
import com.example.patientApplication.Exception.ResourceNotFoundException;
import com.example.patientApplication.Repository.MedicineRepository;
import com.example.patientApplication.Repository.PatientRepository;
import com.example.patientApplication.Service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<MedicineDTO> getMedicinesByContactNumber(String contactNumber) {
        Patient patient = patientRepository.findByContactNumber(contactNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        List<Medicine> medicines = medicineRepository.findByPatient(patient);

        return medicines.stream().map(med -> new MedicineDTO(
                med.getId(),
                med.getName(),
                med.getDosage(),
                med.getInstructions(),
                contactNumber
        )).collect(Collectors.toList());
    }

    @Override
    public void addMedicine(MedicineDTO dto) {
        Patient patient = patientRepository.findByContactNumber(dto.getContactNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Medicine med = new Medicine();
        med.setName(dto.getName());
        med.setDosage(dto.getDosage());
        med.setInstructions(dto.getInstructions());
        med.setPatient(patient);

        medicineRepository.save(med);
    }

    @Override
    public void updateMedicine(Long id, MedicineDTO dto) {
        Medicine med = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found"));

        med.setName(dto.getName());
        med.setDosage(dto.getDosage());
        med.setInstructions(dto.getInstructions());

        medicineRepository.save(med);
    }

    @Override
    public void deleteMedicine(Long id) {
        if (!medicineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medicine not found with id: " + id);
        }
        medicineRepository.deleteById(id);
    }

    @Override
    public MedicineDTO getMedicineById(Long medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + medicineId));
        MedicineDTO dto = new MedicineDTO();
        dto.setId(medicine.getId());
        dto.setName(medicine.getName());
        dto.setDosage(medicine.getDosage());
        dto.setInstructions(medicine.getInstructions());
        return dto;
    }
}
