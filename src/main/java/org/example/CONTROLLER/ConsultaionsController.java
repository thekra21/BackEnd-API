package org.example.CONTROLLER;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.DAO.ConsultaionsDAO;

import org.example.DTO.*;
import org.example.Models.CONSULTATIONS;
import org.example.Models.DOCTORS;


import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/CONSULTAIONS")
@Produces({MediaType.APPLICATION_XML})
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaionsController {


    @Context
    UriInfo uriInfo;
    @Context
    HttpHeaders headers;

    ConsultaionsDAO dao = new ConsultaionsDAO();


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response selectAllConsultations(
            @BeanParam ConsultaionsFliterDTO Fliter
    ) {
        try {
            GenericEntity<ArrayList<ConsultaionsDTO>> consultations = new GenericEntity<ArrayList<ConsultaionsDTO>>(dao.selectAllConsultations()) {
            };
            if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(consultations)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            } else if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
                return Response
                        .ok(consultations)
                        .type("text/csv")
                        .build();
            }
            return Response
                    .ok(consultations, MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET , POST , DELETE , PUT")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //    Doctor should be able to check all pending consultation requests
    @GET
    @Path("/pending/{doctorId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPendingConsultationsForDoctor(@PathParam("doctorId") int doctorId) {
        try {
            // Call DAO method to fetch pending consultations for the given doctorId
            List<PendingConsultationDTO> pendingConsultations = dao.getPendingConsultationsForDoctor(doctorId);

            return Response.ok(pendingConsultations).build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch pending consultations", e);
        }
    }

//    • Doctor should be able to give consultation to a pending request
    @PUT
    @Path("/give-consultation/{consultationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response giveConsultationToPendingRequest(@PathParam("consultationId") int consultationId, CONSULTATIONS consultation) {
        try {
            boolean updated = dao.updateConsultationStatus(consultationId, "Completed", consultation.getDiagnosis());
            if (updated) {
                return Response.ok("Consultation marked as Completed").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Consultation not found or could not be updated").build();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to update consultation status", e);
        }
    }



    @GET
    @Path("/patient/{patientId}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getConsultationResultsForPatient(@PathParam("patientId") int patientId) {
        try {
            List<ConsultaionsDTO> consultationResults = dao.getConsultationResultsForPatient(patientId);

            return Response.ok(consultationResults).build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch consultation results for patient", e);
        }
    }

    // Request a consultation
    @POST
    @Path("/request")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response requestConsultation(ConsultationRequstDTO requestDto) {
        try {
            // Validate request parameters
            if (requestDto.getDoctor_id() <= 0 || requestDto.getPatient_id() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("DoctorId and PatientId must be valid")
                        .build();
            }

            // Set request time to current time
            requestDto.setRequest_time(LocalDateTime.now());

            // Insert consultation request directly into database
            dao.insertConsultationRequest(requestDto);

            return Response.status(Response.Status.CREATED)
                    .entity("Consultation requested successfully")
                    .build();
        } catch (IllegalArgumentException e) {
            // Handle specific validation errors
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (SQLException e) {
            // Handle database errors
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to request consultation: " + e.getMessage())
                    .build();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @PUT
    @Path("/{id}/rate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rateConsultation(@PathParam("id") int consultationId, ConsultaionsDTO consultationDTO) {
        int patientId = consultationDTO.getPatient_id();
        int rating = consultationDTO.getRating();

        try {
            boolean success = dao.updateRating(consultationId, patientId, rating);
            if (success) {
                return Response.ok("Consultation rated successfully").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Patient ID does not match").build();
            }
        } catch (SQLException | ClassNotFoundException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to rate consultation")
                    .build();
        }
    }

    @GET
    @Path("/result/{Id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getConsultationsResult(@PathParam("Id") int Id) {
        try {
            // Call DAO method to fetch pending consultations for the given doctorId
            List<ConsultaionsDTO> CompletedConsultations = dao.getConsultationsResult(Id);

            return Response.ok(CompletedConsultations)
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET , POST , DELETE , PUT")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch result consultations", e);
        }
    }






}



