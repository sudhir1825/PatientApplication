package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.AppointmentDTO;
import com.example.patientApplication.Dto.AppointmentSlotDTO;
import com.example.patientApplication.Service.AppointmentService;
import com.example.patientApplication.Service.AppointmentSlotService;
import com.example.patientApplication.Service.PatientService;
import com.example.patientApplication.Service.impl.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppointmentSlotService slotService;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
    }

    @Test
    public void testShowAppointmentFormWithoutDoctor() throws Exception {
        when(doctorService.getAllDoctors()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/appointments"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    public void testShowAppointmentFormWithDoctor() throws Exception {
        Long doctorId = 1L;
        when(doctorService.getAllDoctors()).thenReturn(Collections.emptyList());
        when(slotService.getAvailableSlotsByDoctor(doctorId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/appointments").param("doctorId", doctorId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(model().attributeExists("slots"))
                .andExpect(model().attributeExists("appointment"))
                .andExpect(model().attributeExists("selectedDoctorId"));

        verify(slotService).getAvailableSlotsByDoctor(doctorId);
    }

    @Test
    public void testBookAppointment() throws Exception {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1234567890");

        AppointmentDTO dto = new AppointmentDTO();
        dto.setSlotId(1L);

        mockMvc.perform(post("/book")
                        .param("slotId", "1")
                        .principal(authentication))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/confirmation"));

        verify(appointmentService).bookAppointment(1L, "1234567890");
    }

    @Test
    public void testShowConfirmation() throws Exception {
        mockMvc.perform(get("/confirmation"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointment-confirmation"));
    }

    @Test
    public void testGetSlotsByDoctor() throws Exception {
        Long doctorId = 1L;
        when(slotService.getAvailableSlotsByDoctor(doctorId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/appointments/slots").param("doctorId", doctorId.toString()))
                .andExpect(status().isOk());

        verify(slotService).getAvailableSlotsByDoctor(doctorId);
    }
}
