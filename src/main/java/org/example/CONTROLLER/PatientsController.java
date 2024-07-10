package org.example.CONTROLLER;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.DAO.DoctorsDAO;
import org.example.DAO.PatientsDAO;
import org.example.DTO.DoctorsDTO;
import org.example.DTO.DoctorsFilterDTO;
import org.example.DTO.PatientsDTO;
import org.example.DTO.PatientsFilterDTO;
import org.example.Mappers.DoctorsMapper;
import org.example.Mappers.PatientsMapper;
import org.example.Models.DOCTORS;
import org.example.Models.PATIENTS;
import org.example.exceptions.DataNotFoundException;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/PATIENTS")
public class PatientsController {

    @Context UriInfo uriInfo;
    @Context HttpHeaders headers;


    PatientsDAO dao = new PatientsDAO();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response getAllPatients(

            @BeanParam PatientsFilterDTO Fliter
    )  {
        try {
            GenericEntity<ArrayList<PATIENTS>> patients = new GenericEntity<ArrayList<PATIENTS>>(dao.selectAllPatients()) {};
            if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(patients)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            }else if(headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
                return Response
                        .ok(patients)
                        .type("text/csv")
                        .build();
            }

            return Response
                    .ok(patients, MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET , POST , DELETE , PUT")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @GET
//    @Path("{id}")
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, "text/csv"})
//    public Response getPatients(@PathParam("id") int id) throws SQLException {
//
//        try {
//            PATIENTS patients= PatientsDAO.selectPatients(id);
//
//            if (patients == null) {
//                throw new DataNotFoundException("Patients" + id + "Not found");
//            }
//            PatientsDTO dto = PatientsMapper.INSTANCE.topatientDTO(patients);
//
//
//
//            return Response.ok(dto).build();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }


@POST
@Path("/register")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public Response registerPatients(PatientsDTO dto) {
    try {
        // Assuming DTO contains necessary fields like name, email, specialty, password, etc.
        PATIENTS patients = PatientsMapper.INSTANCE.toModel(dto);

        // Additional validation logic can be added here
        // For example, check if email already exists, validate password strength, etc.

        if (dao.isEmailExists(dto.getEmail())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Email already exists")
                    .build();
        }
        dao.insertPatients(patients); // Insert the new doctor

        // You can return some response or status if needed
        return Response.status(Response.Status.CREATED)
                .header("Created by", "YourAppName")
                .build();
    } catch (Exception e) {
        throw new RuntimeException(e);
    }

}


}

