package org.example.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.DTO.DoctorsDTO;
import org.example.DTO.DoctorsFilterDTO;
import org.example.DTO.DoctorsnameDTO;
import org.example.Models.CONSULTATIONS;
import org.example.Models.DOCTORS;
import org.example.Models.MedicalReports;
import org.example.Models.SCHEDULES;
import org.example.db.MCPConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;
@ApplicationScoped
public class DoctorsDAO {

    private static final String SELECT_ALL_DOCTORS = "select * from DOCTORS";
    private static final String SELECT_ONE_DOCTORS = "select * from DOCTORS where id = ?";
    private static final String INSERT_DOCTORS = "insert into DOCTORS values (?, ?, ?, ? ,? , ?)";
    private static final String UPDATE_DOCTORS = "update DOCTORS set   lower(name) = ?,lower(specialty) =? , email =? , password =? , phone=? where id = ?";
    private static final String SELECT_ALL_DOCTORS_f = "SELECT d.*,c.rating, s.is_available " +
            "FROM DOCTORS d " +
            "LEFT JOIN CONSULTATIONS c ON d.id = c.doctor_id " +
            "LEFT JOIN SCHEDULES s ON d.id = s.doctor_id";

    private static final String SELECT_DOCTOR_BY_EMAIL = "SELECT * FROM DOCTORS WHERE email = ?";
    private static final String LOGIN_PAT = "select * from DOCTORS where email = ? AND password = ?";

    public void insertDoctors(DOCTORS doctors) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(INSERT_DOCTORS);

        st.setInt(1, doctors.getId());
        st.setString(2, doctors.getName());
        st.setString(3, doctors.getSpecialty());
        st.setString(4, doctors.getEmail());
        st.setString(5, doctors.getPassword());
        st.setString(6, doctors.getPhone());

        int rows = st.executeUpdate();

    }

    public void updateDoctors(DOCTORS doctors) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(UPDATE_DOCTORS);


        st.setInt(6, doctors.getId());
        st.setString(1, doctors.getName());
        st.setString(2, doctors.getName());
        st.setString(3, doctors.getEmail());
        st.setString(4, doctors.getPassword());
        st.setString(5, doctors.getPhone());

        st.executeUpdate();
    }

    public ArrayList<DOCTORS> selectAllDoctors() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(SELECT_ALL_DOCTORS);
        ResultSet rs = st.executeQuery();
        ArrayList<DOCTORS> doctors = new ArrayList<>();
        while (rs.next()) {
            doctors.add(new DOCTORS(rs));
        }

        return doctors;
    }


    public ArrayList<DoctorsDTO> selectDoctorsByCriteria(DoctorsFilterDTO filter) throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        StringBuilder queryBuilder = new StringBuilder(SELECT_ALL_DOCTORS_f);
        boolean isFirstCriteria = true;

        if (filter.getSpecialty() != null) {
            queryBuilder.append(isFirstCriteria ? " WHERE" : " AND");
            queryBuilder.append(" d.specialty = ?");
            isFirstCriteria = false;
        }

        if (filter.getRating() != null) {
            queryBuilder.append(isFirstCriteria ? " WHERE" : " AND");
            queryBuilder.append(" c.rating >= ?");
            isFirstCriteria = false;
        }

        if (filter.getIs_available() != null) {
            queryBuilder.append(isFirstCriteria ? " WHERE" : " AND");
            queryBuilder.append(" s.is_available = ?");
            isFirstCriteria = false;
        }

        if (filter.getId() != null) {
            queryBuilder.append(isFirstCriteria ? " WHERE" : " AND");
            queryBuilder.append(" d.id = ?");
            isFirstCriteria = false;
        }

        if (filter.getName() != null) {
            queryBuilder.append(isFirstCriteria ? " WHERE" : " AND");
            queryBuilder.append(" d.name LIKE ?");
            isFirstCriteria = false;
        }

        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(queryBuilder.toString())) {

            int parameterIndex = 1;

            if (filter.getSpecialty() != null) {
                st.setString(parameterIndex++, filter.getSpecialty());
            }

            if (filter.getRating() != null) {
                st.setInt(parameterIndex++, filter.getRating());
            }

            if (filter.getIs_available() != null) {
                st.setString(parameterIndex++, filter.getIs_available());
            }

            if (filter.getId() != null) {
                st.setInt(parameterIndex++, filter.getId());
            }

            if (filter.getName() != null) {
                st.setString(parameterIndex++, "%" + filter.getName() + "%");
            }

            ResultSet rs = st.executeQuery();
            ArrayList<DoctorsDTO> doctors = new ArrayList<>();
            while (rs.next()) {
                doctors.add(new DoctorsDTO(rs));
            }
            return doctors;
        }
    }


    public boolean isEmailExists(String email) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(SELECT_DOCTOR_BY_EMAIL)) {

            st.setString(1, email);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next(); // true if email exists, false if not
            }
        }
    }


    public DOCTORS DoctorLogin(String patEmail, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(LOGIN_PAT);
        st.setString(1, patEmail);
        st.setString(2, password);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return new DOCTORS(rs);
        } else {
            return null;
        }
    }

    //--------------------------------------------------------------------------------------


}

