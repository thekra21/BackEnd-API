package org.example.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import org.example.DTO.DoctorsFilterDTO;
import org.example.DTO.PatientsDTO;
import org.example.DTO.PatientsFilterDTO;
import org.example.Models.DOCTORS;
import org.example.Models.PATIENTS;
import org.example.db.MCPConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.formatDate;
import static com.sun.tools.javac.util.Constants.format;
import static java.sql.DriverManager.getConnection;

@ApplicationScoped
public class PatientsDAO {

    private static final String SELECT_ALL_PATIENTS = "select * from PATIENTS";
    private static final String SELECT_ONE_PATIENTS = "select * from PATIENTS where id = ?";
    private static final String INSERT_PATIENTS = "insert into PATIENTS values (?, ?, ?, ? ,? , ?)";
    private static final String UPDATE_PATIENTS = "update PATIENTS set   name = ?, email =? , password =? , phone=? , birth_date =? where id = ?";
    private static final String DELETE_PATIENTS = "delete from PATIENTS where id = ?";

    private static final String LOGIN_PAT = "select * from PATIENTS where email = ? AND password = ?";
    private static final String SELECT_PATIENTS_BY_EMAIL = "SELECT * FROM PATIENTS WHERE email = ?";

    public void insertPatients(PATIENTS patients) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(INSERT_PATIENTS);

        st.setInt(1,patients.getId());
        st.setString(2,patients.getName());
        st.setString(3,patients.getEmail());
        st.setString(4,patients.getPassword());
        st.setString(5,patients.getPhone());
        st.setString(6,String.valueOf(patients.getBirth_date()));



        int rows = st.executeUpdate();

    }



    public void updatePatients(PATIENTS patients) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(UPDATE_PATIENTS);


        st.setInt(6,patients.getId());
        st.setString(1,patients.getName());
        st.setString(2,patients.getEmail());
        st.setString(3,patients.getPassword());
        st.setString(4,patients.getPhone());
        st.setString(5, String.valueOf(patients.getBirth_date()));


        st.executeUpdate();
    }


    public static PATIENTS selectPatients(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn =MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_PATIENTS);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return new PATIENTS(rs);
        }
        else {
            return null;
        }
    }

    public ArrayList<PATIENTS> selectAllPatients() throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(SELECT_ALL_PATIENTS);
        ResultSet rs = st.executeQuery();
        ArrayList<PATIENTS> patients = new ArrayList<>();
        while (rs.next()) {
            patients.add(new PATIENTS(rs));
        }

        return patients;
    }
    public PATIENTS PatientsLogin(String patEmail, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn =MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(LOGIN_PAT);
        st.setString(1, patEmail);
        st.setString(2, password);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return new PATIENTS(rs);
        }
        else {
            return null;
        }
    }
    public boolean isEmailExists(String email) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(SELECT_PATIENTS_BY_EMAIL)) {

            st.setString(1, email);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next(); // true if email exists, false if not
            }
        }
    }

}
