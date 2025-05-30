package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.DoctorDTO;
import com.example.patientApplication.Dto.MedicineDTO;
import com.example.patientApplication.Service.impl.AppointmentSlotServiceImpl;
import com.example.patientApplication.Service.impl.DoctorService;
import com.example.patientApplication.Service.impl.MedicineServiceImpl;
import com.example.patientApplication.Service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DoctorService doctorService;

    @Mock
    private AppointmentSlotServiceImpl slotService;

    @Mock
    private MedicineServiceImpl medicineService;

    @Mock
    private PatientServiceImpl patientService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void testShowAdminDashboard() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-dashboard"));
    }

    @Test
    public void testListDoctors() throws Exception {
        when(doctorService.getAllDoctors()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/doctors"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor-list"))
                .andExpect(model().attributeExists("doctors"));

        verify(doctorService).getAllDoctors();
    }

    @Test
    public void testShowDoctorForm() throws Exception {
        mockMvc.perform(get("/admin/doctors/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor-form"))
                .andExpect(model().attributeExists("doctor"));
    }

    @Test
    public void testSaveDoctor() throws Exception {
        mockMvc.perform(post("/admin/doctors")
                        .param("name", "Dr. John")
                        .param("specialization", "Cardiology"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/doctors"));

        verify(doctorService).saveDoctor(any(DoctorDTO.class));
    }

    @Test
    public void testAddSlot() throws Exception {
        mockMvc.perform(post("/admin/slots")
                        .param("doctorId", "1")
                        .param("time", "2025-06-10T10:30:00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/doctors"));

        verify(slotService).addSlot(eq(1L), any(LocalDateTime.class));
    }

    @Test
    public void testShowMedicinesForPatientGet() throws Exception {
        when(medicineService.getMedicinesByContactNumber("12345")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/medicines").param("contactNumber", "12345"))
                .andExpect(status().isOk())
                .andExpect(view().name("medicine-list"))
                .andExpect(model().attributeExists("medicines"))
                .andExpect(model().attributeExists("contactNumber"));
    }

    @Test
    public void testAddMedicine() throws Exception {
        mockMvc.perform(post("/admin/medicines/new")
                        .param("contactNumber", "12345")
                        .param("medicine.name", "Paracetamol"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/medicines?contactNumber=12345"));

        verify(medicineService).addMedicine(any(MedicineDTO.class));
    }

    @Test
    public void testDeleteMedicine() throws Exception {
        mockMvc.perform(post("/admin/medicines/delete")
                        .param("medicineId", "1")
                        .param("contactNumber", "12345"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/medicines?contactNumber=12345"));

        verify(medicineService).deleteMedicine(1L);
    }
}
