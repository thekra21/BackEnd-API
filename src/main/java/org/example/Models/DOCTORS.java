package org.example.Models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DOCTORS {

    private int id;
    private String name ;
    private String specialty;
    private String email;
    private String password;
    private String phone;

    public DOCTORS() {
    }

    public DOCTORS(int id, String name, String email, String specialty, String password, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.specialty = specialty;
        this.password = password;
        this.phone = phone;
    }

    public DOCTORS(ResultSet rs) throws SQLException {

        id = rs.getInt("id");
        name = rs.getString("name");
        email = rs.getString("email");
        specialty = rs.getString("specialty");
        password = rs.getString("password");
        phone = rs.getString("phone");

    }

    @Override
    public String toString() {
        return "DOCTORS{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialty='" + specialty + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
