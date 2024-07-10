package org.example.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.DTO.PatientsFilterDTO;
import org.example.DTO.SchedulesFliterDTO;
import org.example.Models.PATIENTS;

import org.example.Models.SCHEDULES;
import org.example.db.MCPConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;
@ApplicationScoped
public class SchedulesDAO {

    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\hospital\\mcp.db";
    private static final String SELECT_ALL_SCHEDULES = "select * from SCHEDULES";
    private static final String SELECT_ONE_SCHEDULES = "select * from SCHEDULES where id = ?";
    private static final String INSERT_SCHEDULES = "insert into SCHEDULES values (?, ?, ?, ? ,?)";
    private static final String UPDATE_SCHEDULES = "update SCHEDULES set   doctor_id = ?, start_time =? , end_time =? , is_available=?  where id = ?";
    private static final String DELETE_SCHEDULES = "delete from SCHEDULES where id = ?";
    private static final String SELECT_SCHEDULES_WITH_PAGINATION = "select * from SCHEDULES order by id limit ? offset ?";

    private static final String SELECT_AVAILABLE_SCHEDULES_BY_DOCTOR_ID =
            "SELECT * FROM SCHEDULES " +
                    "WHERE doctor_id = ? " +
                    "AND start_time <= ? " +
                    "AND end_time >= ? " +
                    "AND is_available = true";
    private static final String SELECT_SCHEDULES_BY_DOCTOR_ID = "select * from SCHEDULES where doctor_id = ?";
    private static final String DELETE_SCHEDULES_BY_DOCTOR_ID = "delete from SCHEDULES where doctor_id = ?";


    public void insertSchedules(SCHEDULES schedules) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(INSERT_SCHEDULES);

        st.setInt(1, schedules.getId());
        st.setInt(2, schedules.getDoctor_id());
        st.setString(3,String.valueOf( schedules.getStart_time().toString())); // Use setObject to set LocalDateTime
        st.setString(4, String.valueOf(schedules.getEnd_time().toString())); // Use setObject to set LocalDateTime
        st.setBoolean(5, schedules.isIs_available());

        st.executeUpdate();
    }

    public void updateSchedules(SCHEDULES schedules) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn =MCPConnection.getConn();
        PreparedStatement st = conn.prepareStatement(UPDATE_SCHEDULES);

        st.setInt(5, schedules.getId());
        st.setInt(1, schedules.getDoctor_id());
        st.setString(2,String.valueOf( schedules.getStart_time().toString())); // Use setObject to set LocalDateTime
        st.setString(3, String.valueOf(schedules.getEnd_time().toString())); // Use setObject to set LocalDateTime
        st.setBoolean(4, schedules.isIs_available());

        st.executeUpdate();

    }


//    public static SCHEDULES selectSchedules(int id) throws SQLException, ClassNotFoundException {
//        Class.forName("org.sqlite.JDBC");
//        Connection conn =MCPConnection.getConn();
//        PreparedStatement st = conn.prepareStatement(SELECT_ONE_SCHEDULES);
//        st.setInt(1, id);
//        ResultSet rs = st.executeQuery();
//        if(rs.next()) {
//            return new SCHEDULES(rs);
//        }
//        else {
//            return null;
//        }
//    }

    public ArrayList<SCHEDULES> selectAllSchedules(SchedulesFliterDTO Fliter) throws SQLException, ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");
        Connection conn =MCPConnection.getConn();


        PreparedStatement st;


            st = conn.prepareStatement(SELECT_ALL_SCHEDULES);


        ResultSet rs = st.executeQuery();
        ArrayList<SCHEDULES> schedules = new ArrayList<>();
        while (rs.next()) {
            schedules.add(new SCHEDULES(rs));
        }

        return schedules;
    }
    public List<SCHEDULES> selectSchedulesByDoctorId(int doctorId) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        List<SCHEDULES> schedules = new ArrayList<>();

        try (Connection conn = MCPConnection.getConn()) {
            try (PreparedStatement st = conn.prepareStatement(SELECT_SCHEDULES_BY_DOCTOR_ID)) {

                st.setInt(1, doctorId);
                ResultSet rs = st.executeQuery();

                while (rs.next()) {
                    schedules.add(new SCHEDULES(rs));
                }
            }
        }

        return schedules;
    }

    public void deleteSchedulesByDoctorId(int doctorId) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = MCPConnection.getConn();
             PreparedStatement st = conn.prepareStatement(DELETE_SCHEDULES_BY_DOCTOR_ID)) {

            st.setInt(1, doctorId);
            st.executeUpdate();
        }
    }


    }
