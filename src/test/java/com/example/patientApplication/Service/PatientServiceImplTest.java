package com.example.patientApplication.Service;

import com.example.patientApplication.Dto.PatientRegistrationDto;
import com.example.patientApplication.Entity.Patient;
import com.example.patientApplication.Repository.PatientRepository;
import com.example.patientApplication.Service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientServiceImpl patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterNewPatient_Success() {
        // Arrange
        PatientRegistrationDto dto = new PatientRegistrationDto();
        dto.setName("Alice");
        dto.setContactNumber("9876543210");
        dto.setMedicalHistory("Diabetes");
        dto.setPassword("mypassword");

        String encodedPassword = "encodedPassword123";

        Patient expectedPatient = new Patient();
        expectedPatient.setName("Alice");
        expectedPatient.setContactNumber("9876543210");
        expectedPatient.setMedicalHistory("Diabetes");
        expectedPatient.setPassword(encodedPassword);
        expectedPatient.setRole("PATIENT");

        when(passwordEncoder.encode("mypassword")).thenReturn(encodedPassword);
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Patient actualPatient = patientService.registerNewPatient(dto);

        // Assert
        assertNotNull(actualPatient);
        assertEquals("Alice", actualPatient.getName());
        assertEquals("9876543210", actualPatient.getContactNumber());
        assertEquals("Diabetes", actualPatient.getMedicalHistory());
        assertEquals(encodedPassword, actualPatient.getPassword());
        assertEquals("PATIENT", actualPatient.getRole());

        verify(passwordEncoder).encode("mypassword");
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void testFindByContactNumber_Found() {
        // Arrange
        String contactNumber = "1234567890";
        Patient patient = new Patient();
        patient.setContactNumber(contactNumber);
        patient.setName("Bob");
        patient.setMedicalHistory("Hypertension");
        patient.setPassword("encodedPwd");
        patient.setRole("PATIENT");

        when(patientRepository.findByContactNumber(contactNumber)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientService.findByContactNumber(contactNumber);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Bob", result.get().getName());
        assertEquals(contactNumber, result.get().getContactNumber());
        verify(patientRepository).findByContactNumber(contactNumber);
    }

    @Test
    void testFindByContactNumber_NotFound() {
        // Arrange
        String contactNumber = "0000000000";
        when(patientRepository.findByContactNumber(contactNumber)).thenReturn(Optional.empty());

        // Act
        Optional<Patient> result = patientService.findByContactNumber(contactNumber);

        // Assert
        assertFalse(result.isPresent());
        verify(patientRepository).findByContactNumber(contactNumber);
    }
}
