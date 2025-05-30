package com.example.patientApplication.Service;

import com.example.patientApplication.Dto.MedicineDTO;
import com.example.patientApplication.Entity.Medicine;
import com.example.patientApplication.Entity.Patient;
import com.example.patientApplication.Exception.ResourceNotFoundException;
import com.example.patientApplication.Repository.MedicineRepository;
import com.example.patientApplication.Repository.PatientRepository;
import com.example.patientApplication.Service.impl.MedicineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicineServiceImplTest {

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private MedicineServiceImpl medicineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMedicinesByContactNumber_Success() {
        // Arrange
        String contactNumber = "1234567890";
        Patient patient = new Patient();
        patient.setContactNumber(contactNumber);

        List<Medicine> medicines = List.of(
                new Medicine(1L, "Paracetamol", "500mg", "Twice a day", patient),
                new Medicine(2L, "Ibuprofen", "200mg", "Once a day", patient)
        );

        when(patientRepository.findByContactNumber(contactNumber)).thenReturn(Optional.of(patient));
        when(medicineRepository.findByPatient(patient)).thenReturn(medicines);

        // Act
        List<MedicineDTO> result = medicineService.getMedicinesByContactNumber(contactNumber);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Paracetamol", result.get(0).getName());
        verify(patientRepository).findByContactNumber(contactNumber);
        verify(medicineRepository).findByPatient(patient);
    }

    @Test
    void testGetMedicinesByContactNumber_PatientNotFound() {
        // Arrange
        String contactNumber = "notfound";
        when(patientRepository.findByContactNumber(contactNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> medicineService.getMedicinesByContactNumber(contactNumber));
        verify(patientRepository).findByContactNumber(contactNumber);
    }

    @Test
    void testAddMedicine_Success() {
        // Arrange
        String contactNumber = "1234567890";
        Patient patient = new Patient();
        patient.setContactNumber(contactNumber);

        MedicineDTO dto = new MedicineDTO(null, "Amoxicillin", "250mg", "After meals", contactNumber);

        when(patientRepository.findByContactNumber(contactNumber)).thenReturn(Optional.of(patient));

        // Act
        medicineService.addMedicine(dto);

        // Assert
        verify(medicineRepository).save(argThat(med ->
                med.getName().equals("Amoxicillin") &&
                        med.getDosage().equals("250mg") &&
                        med.getInstructions().equals("After meals") &&
                        med.getPatient().equals(patient)
        ));
    }

    @Test
    void testAddMedicine_PatientNotFound() {
        // Arrange
        MedicineDTO dto = new MedicineDTO(null, "DrugX", "10mg", "Before bed", "0000000000");
        when(patientRepository.findByContactNumber("0000000000")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> medicineService.addMedicine(dto));
    }

    @Test
    void testUpdateMedicine_Success() {
        // Arrange
        Long id = 10L;
        Medicine medicine = new Medicine();
        medicine.setId(id);
        medicine.setName("OldName");
        medicine.setDosage("OldDosage");
        medicine.setInstructions("OldInstructions");

        MedicineDTO dto = new MedicineDTO(id, "NewName", "NewDosage", "NewInstructions", null);

        when(medicineRepository.findById(id)).thenReturn(Optional.of(medicine));

        // Act
        medicineService.updateMedicine(id, dto);

        // Assert
        assertEquals("NewName", medicine.getName());
        assertEquals("NewDosage", medicine.getDosage());
        assertEquals("NewInstructions", medicine.getInstructions());
        verify(medicineRepository).save(medicine);
    }

    @Test
    void testUpdateMedicine_MedicineNotFound() {
        // Arrange
        Long id = 999L;
        MedicineDTO dto = new MedicineDTO(id, "X", "Y", "Z", null);
        when(medicineRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> medicineService.updateMedicine(id, dto));
    }

    @Test
    void testDeleteMedicine_Success() {
        // Arrange
        Long id = 1L;
        when(medicineRepository.existsById(id)).thenReturn(true);

        // Act
        medicineService.deleteMedicine(id);

        // Assert
        verify(medicineRepository).deleteById(id);
    }

    @Test
    void testDeleteMedicine_NotFound() {
        // Arrange
        Long id = 999L;
        when(medicineRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> medicineService.deleteMedicine(id));
    }

    @Test
    void testGetMedicineById_Success() {
        // Arrange
        Long id = 5L;
        Medicine med = new Medicine();
        med.setId(id);
        med.setName("Cetirizine");
        med.setDosage("10mg");
        med.setInstructions("Once daily");

        when(medicineRepository.findById(id)).thenReturn(Optional.of(med));

        // Act
        MedicineDTO dto = medicineService.getMedicineById(id);

        // Assert
        assertEquals("Cetirizine", dto.getName());
        assertEquals("10mg", dto.getDosage());
        assertEquals("Once daily", dto.getInstructions());
        verify(medicineRepository).findById(id);
    }

    @Test
    void testGetMedicineById_NotFound() {
        // Arrange
        Long id = 404L;
        when(medicineRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> medicineService.getMedicineById(id));
    }
}
