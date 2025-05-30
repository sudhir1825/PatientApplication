package com.example.patientApplication.Service;

import com.example.patientApplication.Dto.DoctorDTO;
import com.example.patientApplication.Entity.Doctor;
import com.example.patientApplication.Repository.DoctorRepository;
import com.example.patientApplication.Mapper.DoctorMapper;
import com.example.patientApplication.Service.impl.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDoctor() {
        // Given
        DoctorDTO dto = new DoctorDTO(null, "Dr. Jane", "Pediatrics");

        // When
        doctorService.saveDoctor(dto);

        // Then
        verify(doctorRepository).save(argThat(d ->
                d.getName().equals("Dr. Jane") &&
                        d.getSpecialization().equals("Pediatrics")
        ));
    }

    @Test
    void testGetAllDoctors() {
        // Given
        List<Doctor> doctorList = List.of(
                new Doctor(1L, "Dr. Alpha", "Ortho", new ArrayList<>()),
                new Doctor(2L, "Dr. Beta", "Neuro", new ArrayList<>())
        );
        when(doctorRepository.findAll()).thenReturn(doctorList);

        // When
        List<DoctorDTO> result = doctorService.getAllDoctors();

        // Then
        assertEquals(2, result.size());
        assertEquals("Dr. Alpha", result.get(0).getName());
        assertEquals("Neuro", result.get(1).getSpecialization());
    }

    @Test
    void testGetDoctorById_Found() {
        // Given
        Doctor doctor = new Doctor(10L, "Dr. Zeta", "ENT", new ArrayList<>());
        when(doctorRepository.findById(10L)).thenReturn(Optional.of(doctor));

        // When
        Doctor result = doctorService.getDoctorById(10L);

        // Then
        assertNotNull(result);
        assertEquals("Dr. Zeta", result.getName());
    }

    @Test
    void testGetDoctorById_NotFound() {
        // Given
        when(doctorRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> doctorService.getDoctorById(999L));
        assertEquals("Doctor not found", ex.getMessage());
    }
}
