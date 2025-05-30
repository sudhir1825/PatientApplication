package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.AppointmentDTO;
import com.example.patientApplication.Dto.MedicineDTO;
import com.example.patientApplication.Service.AppointmentService;
import com.example.patientApplication.Service.MedicineService;
import com.example.patientApplication.Service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HomeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientServiceImpl patientService;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private MedicineService medicineService;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void testRedirectToHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void testShowHomePage() throws Exception {
        String contactNumber = "9876543210";

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(contactNumber);
        when(appointmentService.getAppointmentsForPatient(contactNumber)).thenReturn(Collections.emptyList());
        when(medicineService.getMedicinesByContactNumber(contactNumber)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/home").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("appointments"))
                .andExpect(model().attributeExists("medicines"));

        verify(appointmentService).getAppointmentsForPatient(contactNumber);
        verify(medicineService).getMedicinesByContactNumber(contactNumber);
    }
}
