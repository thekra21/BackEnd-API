package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ConsultationRequstDTO {

    private int doctor_id;
    private int patient_id;
    private LocalDateTime request_time;

    public ConsultationRequstDTO() {
    }

    public ConsultationRequstDTO(int doctor_id, int patient_id, LocalDateTime request_time) {
        this.doctor_id = doctor_id;
        this.patient_id = patient_id;
        this.request_time = request_time;
    }

    public ConsultationRequstDTO(ResultSet rs) throws SQLException {

        doctor_id = rs.getInt("doctor_id");
        patient_id = rs.getInt("patient_id");
        request_time = rs.getObject("request_time", LocalDateTime.class);


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

    @Override
    public String toString() {
        return "ConsultationRequstDTO{" +
                "doctor_id=" + doctor_id +
                ", patient_id=" + patient_id +
                ", request_time=" + request_time +
                '}';
    }
}
