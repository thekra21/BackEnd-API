package org.example.DTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class PatientsDTO {

    private int id;
    private String name;
    private String email;

    private String phone;
    private LocalDate birth_date;

    public PatientsDTO() {
    }

    public PatientsDTO(int id, String name, String email, String phone, LocalDate birth_date) {
        this.id = id;
        this.name = name;
        this.email = email;

        this.phone = phone;
        this.birth_date = birth_date;
    }

    public PatientsDTO(ResultSet rs) throws SQLException {

        id= rs.getInt("id");
        name=rs.getString("name");
        email=rs.getString("email");
        phone=rs.getString("phone");
       birth_date=rs.getObject("birth_date",LocalDate.class);

    }

    @Override
    public String toString() {
        return "PatientsDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
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
