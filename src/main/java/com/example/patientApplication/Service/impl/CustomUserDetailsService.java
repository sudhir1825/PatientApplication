package com.example.patientApplication.Service.impl;

import com.example.patientApplication.Entity.Patient;
import com.example.patientApplication.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public UserDetails loadUserByUsername(String contactNumber) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByContactNumber(contactNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("Found user: " + patient.getContactNumber() + ", role: " + patient.getRole() +" "+patient.getPassword());

        return User.builder()
                .username(patient.getContactNumber())
                .password(patient.getPassword())
                .roles(patient.getRole()) // "USER" or "ADMIN"
                .build();
    }
}


