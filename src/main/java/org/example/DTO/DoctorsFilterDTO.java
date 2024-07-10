package org.example.DTO;

import jakarta.ws.rs.QueryParam;

public class DoctorsFilterDTO {

//    private  @QueryParam("limit")Integer limit;
//    private @QueryParam("offset")int offset;
    private @QueryParam("specialty")String specialty;
    private @QueryParam("rating")Integer rating;
    private @QueryParam("is_available")String available;
    private @QueryParam("id")Integer id;
    private @QueryParam("name")String name;



    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getIs_available() {
        return available;
    }

    public void setIs_available(String is_available) {
        this.available = is_available;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
