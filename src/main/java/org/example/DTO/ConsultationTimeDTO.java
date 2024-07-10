package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ConsultationTimeDTO {

    private LocalDateTime consultation_time;
    private int id;

    public ConsultationTimeDTO() {
    }

    public ConsultationTimeDTO(LocalDateTime consultation_time, int id) {
        this.consultation_time = consultation_time;
        this.id = id;
    }

    public LocalDateTime getConsultation_time() {
        return consultation_time;
    }

    public void setConsultation_time(LocalDateTime consultation_time) {
        this.consultation_time = consultation_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ConsultationTimeDTO{" +
                "consultation_time=" + consultation_time +
                ", id=" + id +
                '}';
    }

    public ConsultationTimeDTO(ResultSet rs) throws SQLException {

        consultation_time = rs.getObject("consultation_time", LocalDateTime.class);
        id=rs.getInt("id");


    }
}
