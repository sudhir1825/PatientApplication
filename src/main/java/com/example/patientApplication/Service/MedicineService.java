package com.example.patientApplication.Service;


import com.example.patientApplication.Dto.MedicineDTO;

import java.util.List;

public interface MedicineService {
    List<MedicineDTO> getMedicinesByContactNumber(String contactNumber);
    void addMedicine(MedicineDTO medicineDTO);
    void updateMedicine(Long medicineId, MedicineDTO medicineDTO);
    void deleteMedicine(Long medicineId);

    MedicineDTO getMedicineById(Long medicineId);

}
