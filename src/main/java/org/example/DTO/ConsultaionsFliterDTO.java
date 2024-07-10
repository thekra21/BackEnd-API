package org.example.DTO;

import jakarta.ws.rs.QueryParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ConsultaionsFliterDTO {

    private int id;

    public ConsultaionsFliterDTO() {
    }

    public ConsultaionsFliterDTO(int id, int doctor_id, int patient_id, LocalDateTime request_time, LocalDateTime consultation_time, String status, String diagnosis, int rating) {
        this.id = id;

    }


    public ConsultaionsFliterDTO(ResultSet rs) throws SQLException {

        id = rs.getInt("id");


    }

    @Override
    public String toString() {
        return "CONSULTATIONS{" +
                "id=" + id +

                '}';
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }






}
