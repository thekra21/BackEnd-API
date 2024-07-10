package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorsDTO {

    private int id;
    private String name ;
    private String specialty;
    private String email;
    private String phone;


    public DoctorsDTO() {
    }

    public DoctorsDTO(int id, String name, String specialty, String email, String phone) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.email = email;
        this.phone = phone;
    }

    public DoctorsDTO(ResultSet rs) throws SQLException {

        id= rs.getInt("id");
        name=rs.getString("name");
        specialty=rs.getString("specialty");
        email=rs.getString("email");
        phone=rs.getString("phone");

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "DoctorsDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                ", email='" + email + '\'' +

                ", phone='" + phone + '\'' +
                '}';
    }
}
