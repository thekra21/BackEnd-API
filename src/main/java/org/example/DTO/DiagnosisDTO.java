package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DiagnosisDTO {

    private String diagnosis;

    public DiagnosisDTO() {
    }

    public DiagnosisDTO(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public DiagnosisDTO(ResultSet rs) throws SQLException {


        diagnosis = rs.getString("diagnosis");
    }

    @Override
    public String toString() {
        return "DiagnosisDTO{" +
                "diagnosis='" + diagnosis + '\'' +
                '}';
    }


}
