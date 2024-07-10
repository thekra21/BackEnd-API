package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class updetConsultationDTO {

    private String diagnosis;
    private int rating ;
    private int id;


    public updetConsultationDTO() {
    }

    public updetConsultationDTO(String diagnosis, int rating, int id) {
        this.diagnosis = diagnosis;
        this.rating = rating;
        this.id = id;
    }
    public updetConsultationDTO(ResultSet rs) throws SQLException {

        id=rs.getInt("id");
        diagnosis = rs.getString("diagnosis");
        rating = rs.getInt("rating");
    }

    @Override
    public String toString() {
        return "updetConsultationDTO{" +
                "diagnosis='" + diagnosis + '\'' +
                ", rating=" + rating +
                ", id=" + id +
                '}';
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
