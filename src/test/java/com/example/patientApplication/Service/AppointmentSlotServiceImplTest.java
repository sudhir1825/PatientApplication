package com.example.patientApplication.Service;

import com.example.patientApplication.Dto.AppointmentSlotDTO;
import com.example.patientApplication.Entity.AppointmentSlot;
import com.example.patientApplication.Entity.Doctor;
import com.example.patientApplication.Repository.AppointmentSlotRepository;
import com.example.patientApplication.Repository.DoctorRepository;
import com.example.patientApplication.Service.impl.AppointmentSlotServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentSlotServiceImplTest {

    @Mock
    private AppointmentSlotRepository slotRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private AppointmentSlotServiceImpl slotService;

    private Doctor sampleDoctor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleDoctor = new Doctor();
        sampleDoctor.setId(1L);
        sampleDoctor.setName("Dr. Smith");
        sampleDoctor.setSpecialization("Cardiology");
    }

    @Test
    void testAddSlot_WithValidDoctorId_SavesSlot() {
        // Given
        LocalDateTime time = LocalDateTime.of(2025, 6, 1, 10, 0);
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(sampleDoctor));

        // When
        slotService.addSlot(1L, time);

        // Then
        verify(slotRepository).save(argThat(slot ->
                slot.getDoctor().equals(sampleDoctor) &&
                        slot.getSlotTime().equals(time) &&
                        !slot.isBooked()
        ));
    }

    @Test
    void testAddSlot_WithInvalidDoctorId_ThrowsException() {
        when(doctorRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                slotService.addSlot(999L, LocalDateTime.now()));

        assertEquals("Doctor not found with ID: 999", exception.getMessage());
    }

    @Test
    void testGetAvailableSlotsByDoctor_ReturnsDTOs() {
        AppointmentSlot slot = new AppointmentSlot();
        slot.setId(10L);
        slot.setSlotTime(LocalDateTime.of(2025, 6, 5, 14, 0));
        slot.setDoctor(sampleDoctor);
        slot.setBooked(false);

        when(slotRepository.findByDoctorIdAndBookedFalse(1L)).thenReturn(List.of(slot));

        List<AppointmentSlotDTO> result = slotService.getAvailableSlotsByDoctor(1L);

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getId());
        assertEquals(sampleDoctor.getId(), result.get(0).getDoctorId());
    }

    @Test
    void testGetSlotById_WhenExists_ReturnsDTO() {
        AppointmentSlot slot = new AppointmentSlot();
        slot.setId(20L);
        slot.setSlotTime(LocalDateTime.of(2025, 7, 1, 9, 0));
        slot.setDoctor(sampleDoctor);

        when(slotRepository.findById(20L)).thenReturn(Optional.of(slot));

        AppointmentSlotDTO dto = slotService.getSlotById(20L);

        assertNotNull(dto);
        assertEquals(20L, dto.getId());
        assertEquals(sampleDoctor.getId(), dto.getDoctorId());
    }

    @Test
    void testGetSlotById_WhenNotFound_ThrowsException() {
        when(slotRepository.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> slotService.getSlotById(99L));
        assertEquals("Slot not found", ex.getMessage());
    }
}
