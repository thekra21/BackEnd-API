package org.example.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MedicalReports {

    private int id;
    private int patient_id;
    private String details;
    private LocalDateTime report_date;
    private int consultation_id;

    public MedicalReports() {
    }

    public MedicalReports(int id, int patient_id, String details, LocalDateTime report_date, int consultation_id) {
        this.id = id;
        this.patient_id = patient_id;
        this.details = details;
        this.report_date = report_date;
        this.consultation_id = consultation_id;
    }

    public MedicalReports(ResultSet rs) throws SQLException {

        id = rs.getInt("id");
        patient_id = rs.getInt("patient_id");
        details = rs.getString("details");
        report_date =rs.getObject("report_date", LocalDateTime.class);
        consultation_id = rs.getInt("consultation_id");
    }

    @Override
    public String toString() {
        return "MedicalReports{" +
                "id=" + id +
                ", patient_id=" + patient_id +
                ", details='" + details + '\'' +
                ", report_date=" + report_date +
                ", consultation_id=" + consultation_id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getReport_date() {
        return report_date;
    }

    public void setReport_date(LocalDateTime report_date) {
        this.report_date = report_date;
    }

    public int getConsultation_id() {
        return consultation_id;
    }

    public void setConsultation_id(int consultation_id) {
        this.consultation_id = consultation_id;
    }
}
