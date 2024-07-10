package org.example.CONTROLLER;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.example.DAO.MedicalReportsDAO;
import org.example.DTO.ConsultaionsDTO;
import org.example.DTO.DoctorsFilterDTO;
import org.example.DTO.MedicalReportsConsultaionDTO;
import org.example.DTO.MedicalReportsFliterDTO;
import org.example.Models.DOCTORS;
import org.example.Models.MedicalReports;

import java.util.ArrayList;
import java.util.List;

@Path("/MedicalReports")
@Produces({MediaType.APPLICATION_XML})
@Consumes(MediaType.APPLICATION_JSON)
public class MedicalReportsController {

    @Context
    UriInfo uriInfo;
    @Context
    HttpHeaders headers;

    MedicalReportsDAO dao =new MedicalReportsDAO();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, "text/csv"})
    public Response getAllMedicalReports(
                @BeanParam MedicalReportsFliterDTO Fliter
    ) {
        try {
            GenericEntity<ArrayList<MedicalReports>> MedicalReports = new GenericEntity<ArrayList<MedicalReports>>(dao.selectAllMedicalReports(Fliter)) {
            };
            if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(MedicalReports)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            } else if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf("text/csv"))) {
                return Response
                        .ok(MedicalReports)
                        .type("text/csv")
                        .build();
            }
            return Response
                    .ok(MedicalReports, MediaType.APPLICATION_JSON)
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET , POST , DELETE , PUT")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GET
    @Path("/patient/{patientId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMedicalReportsByPatientId(@PathParam("patientId") int patientId) {
        try {
            // Retrieve medical reports for the patient
            ArrayList<MedicalReports> medicalReports = dao.selectMedicalReportsByPatientId(patientId);

            // Retrieve consultations for the patient
            List<ConsultaionsDTO> consultations = dao.getConsultationResultsForPatient(patientId);

            // Combine medical reports and consultations into a single DTO
            MedicalReportsConsultaionDTO resultDTO = new MedicalReportsConsultaionDTO();
            resultDTO.setMedicalReports(medicalReports.get(0)); // Assuming you want to associate with the first medical report
            resultDTO.setConsultations(consultations);

            return Response.ok(resultDTO)
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET , POST , DELETE , PUT")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch medical reports and consultations for patient: " + patientId, e);
        }
    }

}
