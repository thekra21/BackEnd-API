package org.example.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.DTO.ConsultaionsDTO;
import org.example.DTO.MedicalReportsFliterDTO;
import org.example.DTO.PatientsFilterDTO;
import org.example.Models.CONSULTATIONS;
import org.example.Models.MedicalReports;
import org.example.Models.PATIENTS;
import org.example.db.MCPConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped

public class MedicalReportsDAO {

    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\hospital\\mcp.db";
    private static final String SELECT_ALL_MedicalReports = "select * from MEDICAL_REPORTS";
    private static final String SELECT_ONE_MedicalReports = "select * from MEDICAL_REPORTS where id = ?";
    private static final String INSERT_MedicalReports = "insert into MEDICAL_REPORTS values (?, ?, ?, ? ,? )";
    private static final String UPDATE_MedicalReports = "update MEDICAL_REPORTS set  patient_id = ?, details =? , report_date =? , consultation_id=?  where id = ?";
    private static final String DELETE_MedicalReports = "delete from MEDICAL_REPORTS where id = ?";
    private static final String SELECT_MedicalReports_WITH_PAGINATION = "select * from MEDICAL_REPORTS order by id limit ? offset ?";

    private static final String SELECT_MEDICAL_REPORTS_BY_PATIENT_ID = "select * from MEDICAL_REPORTS where patient_id = ?";



    public void insertMedicalReports(MedicalReports medicalReports) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(INSERT_MedicalReports);

        st.setInt(1, medicalReports.getId());
        st.setInt(2, medicalReports.getPatient_id());
        st.setString(3, medicalReports.getDetails());
        st.setString(4, String.valueOf(medicalReports.getReport_date())); // Use setObject to set LocalDateTime
        st.setInt(5, medicalReports.getConsultation_id());


        int rows = st.executeUpdate();
    }

    public void updateMedicalReports(MedicalReports medicalReports) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn =MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(UPDATE_MedicalReports);

        st.setInt(5, medicalReports.getId());
        st.setInt(1, medicalReports.getPatient_id());
        st.setString(2, medicalReports.getDetails());
        st.setString(3, String.valueOf(medicalReports.getReport_date())); // Use setObject to set LocalDateTime
        st.setInt(4, medicalReports.getConsultation_id());


        st.executeUpdate();

    }

    public void deleteMedicalReports(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn =MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(DELETE_MedicalReports);
        st.setInt(1, id);
        st.executeUpdate();
    }

    public static MedicalReports selectMedicalReport(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn =MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_MedicalReports);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return new MedicalReports(rs);
        } else {
            return null;
        }
    }

    public ArrayList<MedicalReports> selectAllMedicalReports(MedicalReportsFliterDTO Fliter) throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        Connection conn =MCPConnection.getConn();


        PreparedStatement st;

        if (Fliter.getLimit() != null) {
            st = conn.prepareStatement(SELECT_MedicalReports_WITH_PAGINATION);
            st.setInt(1, Fliter.getLimit());
            st.setInt(2, Fliter.getOffset());
        } else {
            st = conn.prepareStatement(SELECT_ALL_MedicalReports);

        }
        ResultSet rs = st.executeQuery();
        ArrayList<MedicalReports> medicalReports = new ArrayList<>();
        while (rs.next()) {
            medicalReports.add(new MedicalReports(rs));
        }

        return medicalReports;
    }

    public ArrayList<MedicalReports> selectMedicalReportsByPatientId(int patientId) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(SELECT_MEDICAL_REPORTS_BY_PATIENT_ID);
        st.setInt(1, patientId);
        ResultSet rs = st.executeQuery();

        ArrayList<MedicalReports> medicalReports = new ArrayList<>();
        while (rs.next()) {
            medicalReports.add(new MedicalReports(rs));
        }

        return medicalReports;
    }

    public List<ConsultaionsDTO> getConsultationResultsForPatient(int patientId) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        List<ConsultaionsDTO> consultationResults = new ArrayList<>();

        String SELECT_CONSULTATION_RESULTS_FOR_PATIENT = "SELECT * FROM CONSULTATIONS WHERE patient_id = ?";
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(SELECT_CONSULTATION_RESULTS_FOR_PATIENT)) {

            st.setInt(1, patientId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                consultationResults.add(new ConsultaionsDTO(rs));
            }
        }

        return consultationResults;
    }




}

