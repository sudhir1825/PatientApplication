package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.PatientRegistrationDto;
import com.example.patientApplication.Entity.Patient;
import com.example.patientApplication.Service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        String contact = "9876543210";

        when(patientService.findByContactNumber(contact)).thenReturn(Optional.empty());

        mockMvc.perform(post("/register")
                        .param("name", "Test User")
                        .param("contactNumber", contact)
                        .param("password", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?success"));

        verify(patientService).registerNewPatient(any(PatientRegistrationDto.class));
    }

    @Test
    public void testRegisterUser_ContactAlreadyExists() throws Exception {
        String contact = "9876543210";

        Patient mockPatient = new Patient();  // or use a Mockito mock if preferred

        when(patientService.findByContactNumber(contact)).thenReturn(Optional.of(mockPatient));

        mockMvc.perform(post("/register")
                        .param("name", "Test User")
                        .param("contactNumber", contact)
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("contactError"));
    }

    @Test
    public void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
