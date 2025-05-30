package com.example.patientApplication.Dto;



public class MedicineDTO {
    private Long id;
    private String name;
    private String dosage;
    private String instructions;
    private String contactNumber; // NEW FIELD

    public MedicineDTO() {
    }

    public MedicineDTO(Long id, String name, String dosage, String instructions, String contactNumber) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.instructions = instructions;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}

