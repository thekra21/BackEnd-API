package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PendingConsultationDTO {

    private int id;
    private int doctor_id;
    private int patient_id;
    private String status;

    public PendingConsultationDTO() {
    }

    public PendingConsultationDTO(int id, int doctor_id, int patient_id, String status) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.status = status;
    }

    public PendingConsultationDTO(ResultSet rs) throws SQLException {

        id = rs.getInt("id");
        doctor_id = rs.getInt("doctor_id");
        patient_id = rs.getInt("patient_id");
        status = rs.getString("status");

    }

    @Override
    public String toString() {
        return "PendingConsultationDTO{" +
                "id=" + id +
                ", doctor_id=" + doctor_id +
                ", patient_id=" + patient_id +
                ", status='" + status + '\'' +
                '}';
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
