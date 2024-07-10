package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ConsultaionsDTO {


    private int doctor_id;
    private int patient_id;
    private LocalDateTime request_time;
    private LocalDateTime consultation_time;
    private String status;
    private String diagnosis;
    private int rating;


    public ConsultaionsDTO() {
    }

    public ConsultaionsDTO( int doctor_id, int patient_id, LocalDateTime request_time, LocalDateTime consultation_time, String status, String diagnosis, int rating) {

        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.request_time = request_time;
        this.consultation_time = consultation_time;
        this.status = status;
        this.diagnosis = diagnosis;
        this.rating = rating;
    }

    public ConsultaionsDTO(ResultSet rs) throws SQLException {

        doctor_id = rs.getInt("doctor_id");
        patient_id = rs.getInt("patient_id");
        request_time = rs.getObject("request_time", LocalDateTime.class);
        consultation_time = rs.getObject("consultation_time", LocalDateTime.class);
        status = rs.getString("status");
        diagnosis = rs.getString("diagnosis");
        rating = rs.getInt("rating");
    }

    @Override
    public String toString() {
        return "ConsultaionsDTO{" +
                ", doctor_id=" + doctor_id +
                ", patient_id=" + patient_id +
                ", request_time=" + request_time +
                ", consultation_time=" + consultation_time +
                ", status='" + status + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", rating=" + rating +
                '}';
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public LocalDateTime getRequest_time() {
        return request_time;
    }

    public void setRequest_time(LocalDateTime request_time) {
        this.request_time = request_time;
    }

    public LocalDateTime getConsultation_time() {
        return consultation_time;
    }

    public void setConsultation_time(LocalDateTime consultation_time) {
        this.consultation_time = consultation_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
