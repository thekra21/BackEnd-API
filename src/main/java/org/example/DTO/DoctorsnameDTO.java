package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorsnameDTO {

    private int id;
    private String name ;
    private String specialty;

    public DoctorsnameDTO(ResultSet rs) throws SQLException {

        id = rs.getInt("id");
        name = rs.getString("name");
        specialty = rs.getString("specialty");
    }

    public DoctorsnameDTO() {

    }

    public DoctorsnameDTO(int id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "DoctorsnameDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
