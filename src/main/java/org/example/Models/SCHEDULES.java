package org.example.Models;

import jakarta.ws.rs.FormParam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class SCHEDULES {

    private int id ;
    private int doctor_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    @FormParam("is_available")
    private boolean is_available;

    public SCHEDULES() {
    }

    public SCHEDULES(int id, LocalDateTime start_time, int doctor_id, LocalDateTime end_time, boolean is_available) {
        this.id = id;
        this.start_time = start_time;
        this.doctor_id = doctor_id;
        this.end_time = end_time;
        this.is_available = is_available;
    }

    public SCHEDULES(ResultSet rs) throws SQLException {

        id = rs.getInt("id");
        doctor_id = rs.getInt("doctor_id");
        start_time = rs.getObject("start_time",LocalDateTime.class);
        end_time = rs.getObject("end_time",LocalDateTime.class);
        is_available = rs.getBoolean("is_available");
    }

    @Override
    public String toString() {
        return "SCHEDULES{" +
                "id=" + id +
                ", doctor_id=" + doctor_id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", is_available=" + is_available +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }
}
