package org.example.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class PATIENTS {

    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate birth_date;

    public PATIENTS() {
    }

    public PATIENTS(int id, String name, String email, String password, String phone, LocalDate birth_date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birth_date = birth_date;
    }

    public PATIENTS(ResultSet rs) throws SQLException{

        id = rs.getInt("id");
        name = rs.getString("name");
        email = rs.getString("email");
        password = rs.getString("password");
        phone = rs.getString("phone");

        birth_date = LocalDate.parse(rs.getString("birth_date"));



    }

    @Override
    public String toString() {
        return "PATIENTS{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", birth_date=" + birth_date +
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


    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }
}
