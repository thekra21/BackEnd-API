package org.example.CONTROLLER;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.DAO.ConsultaionsDAO;
import org.example.DAO.DoctorsDAO;
import org.example.DTO.DoctorsDTO;
import org.example.DTO.DoctorsFilterDTO;
import org.example.DTO.DoctorsnameDTO;
import org.example.Mappers.DoctorsMapper;
import org.example.Models.CONSULTATIONS;
import org.example.Models.DOCTORS;
import org.example.Models.SCHEDULES;
import org.example.exceptions.DataNotFoundException;

import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/DOCTORS")
@Produces({MediaType.APPLICATION_XML})
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorsController {

    @Context
    UriInfo uriInfo;
    @Context
    HttpHeaders headers;



    DoctorsDAO dao = new DoctorsDAO();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response getAllDoctors(
            @BeanParam DoctorsFilterDTO Fliter
    ) {
        try {
            GenericEntity<ArrayList<DOCTORS>> doctors = new GenericEntity<ArrayList<DOCTORS>>(dao.selectAllDoctors()) {
            };
            if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(doctors)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            } else if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
                return Response
                        .ok(doctors)
                        .type("text/csv")
                        .build();
            }
            return Response
                    .ok(doctors, MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET , POST , DELETE , PUT")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/search")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response getAllDoctorsFltir(
            @BeanParam DoctorsFilterDTO Fliter
    ) {
        try {
            GenericEntity<ArrayList<DoctorsDTO>> doctors = new GenericEntity<ArrayList<DoctorsDTO>>(dao.selectDoctorsByCriteria(Fliter)) {
            };
            if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(doctors)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            } else if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
                return Response
                        .ok(doctors)
                        .type("text/csv")
                        .build();
            }
            return Response
                    .ok(doctors, MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET , POST , DELETE , PUT")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Path("/register")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response registerDoctor(DoctorsDTO dto) {
        try {
            // Assuming DTO contains necessary fields like name, email, specialty, password, etc.
            DOCTORS doctor = DoctorsMapper.INSTANCE.toModel(dto);

            // Additional validation logic can be added here
            // For example, check if email already exists, validate password strength, etc.

            if (dao.isEmailExists(dto.getEmail())) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Email already exists")
                        .build();
            }
            dao.insertDoctors(doctor); // Insert the new doctor

            // You can return some response or status if needed
            return Response.status(Response.Status.CREATED)
                    .header("Created by", "YourAppName")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}




