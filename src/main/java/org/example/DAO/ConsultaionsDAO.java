package org.example.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.DTO.ConsultaionsDTO;
import org.example.DTO.ConsultationRequstDTO;
import org.example.DTO.ConsultationTimeDTO;
import org.example.DTO.PendingConsultationDTO;
import org.example.Models.CONSULTATIONS;
import org.example.db.MCPConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaionsDAO {

    private static final String SELECT_ALL_CONSULTATIONS = "select * from CONSULTATIONS";
    private static final String SELECT_ONE_CONSULTATIONS = "select * from CONSULTATIONS where id = ?";
    private static final String INSERT_CONSULTATIONS = "insert into CONSULTATIONS values (?, ?, ?, ? ,? , ?,?,?)";
    private static final String UPDATE_CONSULTATIONS = "update CONSULTATIONS set doctor_id = ?, patient_id =? , request_time =? , consultation_time=? , status =? , diagnosis =? , rating =? where id = ?";
    private static final String DELETE_CONSULTATIONS = "delete from CONSULTATIONS where id = ?";
    private static final String SELECT_CONSULTATIONS_WITH_PAGINATION = "select * from CONSULTATIONS order by id limit ? offset ?";
    private static final String UPDATE_RATING = "UPDATE CONSULTATIONS SET rating = ? WHERE id = ? AND patient_id = ?";
    private static final String CHECK_SQL = "SELECT patient_id FROM CONSULTATIONS WHERE id = ?";

    public static CONSULTATIONS selectConsultations(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn(); PreparedStatement st = conn.prepareStatement(SELECT_ONE_CONSULTATIONS)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new CONSULTATIONS(rs); // Assuming CONSULTATIONS constructor takes ResultSet
                } else {
                    return null; // Consultation not found
                }
            }
        }
    }

    public void insertConsultations(CONSULTATIONS consultations) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(INSERT_CONSULTATIONS);

        st.setInt(1, consultations.getId());
        st.setInt(2, consultations.getDoctor_id());
        st.setInt(3, consultations.getPatient_id());
        st.setString(4, consultations.getRequest_time().toString()); // Use setObject to set LocalDateTime
        st.setString(5, consultations.getConsultation_time().toString());
        st.setString(6, consultations.getStatus());
        st.setString(7, consultations.getDiagnosis());
        st.setInt(8, consultations.getRating());

        int rows = st.executeUpdate();
    }

    public void updateConsultations(CONSULTATIONS consultations) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(UPDATE_CONSULTATIONS);

        st.setInt(8, consultations.getId());
        st.setInt(1, consultations.getDoctor_id());
        st.setInt(2, consultations.getPatient_id());
        st.setString(3, String.valueOf(consultations.getRequest_time()));
//        st.setObject(3, consultations.getRequest_time()); // Use setObject to set LocalDateTime
        st.setString(4, String.valueOf(consultations.getConsultation_time()));
        st.setString(5, consultations.getStatus());
        st.setString(6, consultations.getDiagnosis());
        st.setInt(7, consultations.getRating());

        st.executeUpdate();
    }

    public void deleteconsultations(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(DELETE_CONSULTATIONS);
        st.setInt(1, id);
        st.executeUpdate();
    }

    public ArrayList<ConsultaionsDTO> selectAllConsultations() throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();


        PreparedStatement st;

        st = conn.prepareStatement(SELECT_ALL_CONSULTATIONS);
        ResultSet rs = st.executeQuery();
        ArrayList<ConsultaionsDTO> consultations = new ArrayList<>();
        while (rs.next()) {
            consultations.add(new ConsultaionsDTO(rs));
        }

        return consultations;
    }


    public List<PendingConsultationDTO> getPendingConsultationsForDoctor(int doctorId) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        List<PendingConsultationDTO> PendingConsultationDTO = new ArrayList<>();

        // Assuming you have a table named CONSULTATIONS with relevant fields

        String SELECT_PENDING_CONSULTATIONS = "SELECT * FROM CONSULTATIONS WHERE doctor_id = ? AND status = 'Pending'";


        try (Connection conn = MCPConnection.getConn(); PreparedStatement st = conn.prepareStatement(SELECT_PENDING_CONSULTATIONS)) {

            st.setInt(1, doctorId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                PendingConsultationDTO.add(new PendingConsultationDTO(rs));
            }
        }

        return PendingConsultationDTO;
    }

    public List<ConsultaionsDTO> getConsultationsResult(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        List<ConsultaionsDTO> consultationsresult = new ArrayList<>();
        String SELECT_CONSULTATIONS_RESULT = "SELECT * FROM CONSULTATIONS WHERE id = ? AND status = 'Completed'";

        try (Connection conn = MCPConnection.getConn(); PreparedStatement st = conn.prepareStatement(SELECT_CONSULTATIONS_RESULT)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                consultationsresult.add(new ConsultaionsDTO(rs));
            }
        }

        return consultationsresult;
    }

    public boolean updateConsultationStatus(int consultationId, String status, String diagnosis) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        // Get current timestamp
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedConsultationTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String UPDATE_CONSULTATION_STATUS = "UPDATE CONSULTATIONS SET status = ?, diagnosis = ?, consultation_time = ? WHERE id = ?";

        try (Connection conn = MCPConnection.getConn(); PreparedStatement st = conn.prepareStatement(UPDATE_CONSULTATION_STATUS)) {
            st.setString(1, status);
            st.setString(2, diagnosis);
            st.setString(3, formattedConsultationTime);
            st.setInt(4, consultationId);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public boolean updateRating(int id, int patientId, int rating) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        try (Connection connection = MCPConnection.getConn(); PreparedStatement checkStmt = connection.prepareStatement(CHECK_SQL); PreparedStatement updateStmt = connection.prepareStatement(UPDATE_RATING)) {

            // Check if the patient ID matches the consultation ID
            checkStmt.setInt(1, id);
            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next() && resultSet.getInt("patient_id") == patientId) {
                    updateStmt.setInt(1, rating);
                    updateStmt.setInt(2, id);
                    updateStmt.setInt(3, patientId);
                    int rowsAffected = updateStmt.executeUpdate();
                    return rowsAffected > 0;
                }
            }
        }
        return false;
    }

    public List<ConsultaionsDTO> getConsultationResultsForPatient(int patientId) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        List<ConsultaionsDTO> consultationResults = new ArrayList<>();

        String SELECT_CONSULTATION_RESULTS_FOR_PATIENT = "SELECT * FROM CONSULTATIONS WHERE patient_id = ?";
        try (Connection conn = MCPConnection.getConn(); PreparedStatement st = conn.prepareStatement(SELECT_CONSULTATION_RESULTS_FOR_PATIENT)) {

            st.setInt(1, patientId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                consultationResults.add(new ConsultaionsDTO(rs));
            }
        }

        return consultationResults;
    }



    public void insertConsultationRequest(ConsultationRequstDTO consultationRequstDTO) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String formattedRequestTime;
        LocalDateTime requestTime = consultationRequstDTO.getRequest_time();
        if (requestTime != null) {
            formattedRequestTime = requestTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        } else {
            throw new IllegalArgumentException("Request time cannot be null");
        }

        String INSERT_REQUEST_CONSULTATION =
                "INSERT INTO CONSULTATIONS " +
                        "(doctor_id,patient_id,request_time,consultation_time,status,diagnosis) " +
                        "VALUES " +
                        "(\n" + "  '" + consultationRequstDTO.getDoctor_id() + "',\n" + "  '" + consultationRequstDTO.getPatient_id() + "',\n" + "  '" + formattedRequestTime + "',\n" + "  '',\n" + "  'Pending',\n" + "  ''\n" + ") ";
        try (Connection conn = MCPConnection.getConn(); PreparedStatement st = conn.prepareStatement(INSERT_REQUEST_CONSULTATION)) {
            st.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//    public boolean updateConsultation(ConsultationTimeDTO consultationTimeDTO) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        String formattedConsultationTime;
//        LocalDateTime ConsultationTime = consultationTimeDTO.getConsultation_time();
//        if (ConsultationTime != null) {
//            formattedConsultationTime = ConsultationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//        } else {
//            throw new IllegalArgumentException("Consultation Time cannot be null");
//        }
//        String UPDATE_CONSULTATION = "UPDATE CONSULTATIONS SET consultation_time = ? WHERE consultation_id = ?";
//        try (Connection conn = MCPConnection.getConn(); PreparedStatement st = conn.prepareStatement(UPDATE_CONSULTATION)) {
//            st.setString(1, formattedConsultationTime);
//            st.setInt(2, consultationTimeDTO.getId());
//            int rowsUpdated = st.executeUpdate();
//            return rowsUpdated > 0;
//        }
//
//    }
}



