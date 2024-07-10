package org.example.DTO;

import org.example.Models.MedicalReports;

import java.util.List;

public class MedicalReportsConsultaionDTO {

    private MedicalReports medicalReports;  // Holds a single MedicalReports object
    private List<ConsultaionsDTO> consultations;  // Holds a list of ConsultaionsDTO objects

    // Constructor
    public MedicalReportsConsultaionDTO() {
    }

    // Getter for medicalReports
    public MedicalReports getMedicalReports() {
        return medicalReports;
    }

    // Setter for medicalReports
    public void setMedicalReports(MedicalReports medicalReports) {
        this.medicalReports = medicalReports;
    }

    // Getter for consultations
    public List<ConsultaionsDTO> getConsultations() {
        return consultations;
    }

    // Setter for consultations
    public void setConsultations(List<ConsultaionsDTO> consultations) {
        this.consultations = consultations;
    }
}

