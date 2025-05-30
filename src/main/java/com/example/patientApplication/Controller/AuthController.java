package com.example.patientApplication.Controller;

import com.example.patientApplication.Dto.PatientRegistrationDto;
import com.example.patientApplication.Service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("patient", new PatientRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("patient") @Valid PatientRegistrationDto patientDto,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        String contact = patientDto.getContactNumber().trim();

        if (patientService.findByContactNumber(contact).isPresent()) {
            model.addAttribute("contactError", "Contact number already used");
            return "register";
        }

        patientDto.setContactNumber(contact);
        patientService.registerNewPatient(patientDto);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

