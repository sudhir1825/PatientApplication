package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.PatientRegistrationDto;
import com.example.patientApplication.Service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid PatientRegistrationDto patientDto,
                                          BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed: " + result.getAllErrors());
        }

        String contact = patientDto.getContactNumber().trim();
        if (patientService.findByContactNumber(contact).isPresent()) {
            return ResponseEntity.badRequest().body("Contact number already used");
        }

        patientDto.setContactNumber(contact);
        patientService.registerNewPatient(patientDto);
        return ResponseEntity.ok("Registration successful");
    }

    // Optionally add an endpoint to verify login status (Spring Security handles the login itself)
    @GetMapping("/login-status")
    public ResponseEntity<String> loginStatus() {
        return ResponseEntity.ok("Login page or status (for frontend handling)");
    }
}
