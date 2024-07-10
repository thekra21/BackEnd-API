package org.example.CONTROLLER;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.DAO.SchedulesDAO;
import org.example.DTO.DoctorsFilterDTO;
import org.example.DTO.SchedulesFliterDTO;
import org.example.Mappers.PatientsMapper;
import org.example.Mappers.SchedulesMapper;
import org.example.Models.DOCTORS;
import org.example.Models.PATIENTS;
import org.example.Models.SCHEDULES;

import java.util.ArrayList;
import java.util.List;


@Path("/SCHEDULES")
@Produces({MediaType.APPLICATION_XML})
@Consumes(MediaType.APPLICATION_JSON)
public class SchedulesController {

    @Context
    UriInfo uriInfo;
    @Context
    HttpHeaders headers;

    SchedulesDAO dao =new SchedulesDAO();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response getAllSchedules(
            @BeanParam SchedulesFliterDTO Fliter
    ) {
        try {
            GenericEntity<ArrayList<SCHEDULES>>  Schedules= new GenericEntity<ArrayList<SCHEDULES>>(dao.selectAllSchedules(Fliter)) {
            };
            if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(Schedules)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            } else if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
                return Response
                        .ok(Schedules)
                        .type("text/csv")
                        .build();
            }
            return Response
                    .ok(Schedules, MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Path("/{doctorId}/schedule")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response addSchedule(@PathParam("doctorId") int doctorId, SCHEDULES schedule) {
        try {

            schedule.setDoctor_id(doctorId);
            dao.insertSchedules(schedule);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to add schedule", e);
        }
    }
    @PUT
    @Path("/{doctorId}/schedule")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updetSchedule(@PathParam("doctorId") int doctorId, SCHEDULES schedule) {
        try {
            schedule.setDoctor_id(doctorId);
            dao.updateSchedules(schedule);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to updete schedule", e);
        }
    }

    @GET
    @Path("/{doctorId}/schedule")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getSchedules(@PathParam("doctorId") int doctorId) {
        try {
            List<SCHEDULES> schedules = dao.selectSchedulesByDoctorId(doctorId);
            return Response.ok(schedules).build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch schedules", e);
        }
    }

    @DELETE
    @Path("/{doctorId}/schedule")
    public Response deleteSchedules(@PathParam("doctorId") int doctorId) {
        try {
            dao.deleteSchedulesByDoctorId(doctorId);
            return Response.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete schedules", e);
        }
    }


}
