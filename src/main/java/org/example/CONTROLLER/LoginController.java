package org.example.CONTROLLER;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.Response;
import org.example.DAO.DoctorsDAO;
import org.example.DAO.LoginDAO;
import org.example.DAO.PatientsDAO;
import org.example.DTO.LoginDTO;
import org.example.DTO.LoginRespondDTO;
import org.example.Models.DOCTORS;
import org.example.Models.Login;
import org.example.Models.PATIENTS;

import java.sql.SQLException;
import java.util.ArrayList;

@Path("/Login")
public class LoginController {

    static LoginDAO loginDAO = new LoginDAO();
    PatientsDAO patientDAO = new PatientsDAO();
    DoctorsDAO doctorDAO = new DoctorsDAO();

    @POST
    public Response login(LoginDTO loginDTO) throws SQLException {

        LoginRespondDTO loginRespondDTO;
        try
        {
            Login login = loginDAO.getLogin(loginDTO.getLOGIN_email(), loginDTO.getLOGIN_password());

            GenericEntity<ArrayList<DOCTORS>> doctors = new GenericEntity<ArrayList<DOCTORS>>(doctorDAO.selectAllDoctors()) {};
            //select * from DOCTOR;

            GenericEntity<ArrayList<PATIENTS>> patients = new GenericEntity<ArrayList<PATIENTS>>(patientDAO.selectAllPatients()) {};
            //select * from PATIENT;

            if (login != null)
            {
                if (login.getLOGIN_Type().equals("DOCTOR")) {

                    for (DOCTORS doctor : doctors.getEntity()) {
                        if (doctor.getEmail().equals(loginDTO.getLOGIN_email()) && doctor.getPassword().equals(loginDTO.getLOGIN_password())) {

                            Integer doctorID = doctor.getId();
                            loginRespondDTO = new LoginRespondDTO(doctorID, login.getLOGIN_Type());
                            return Response
                                    .ok(loginRespondDTO)

                                    .build();
                        }
                    }
                }
                else
                {
                    for (PATIENTS patient : patients.getEntity()) {
                        if (patient.getEmail().equals(loginDTO.getLOGIN_email()) && patient.getPassword().equals(loginDTO.getLOGIN_password())) {

                            Integer patientID = patient.getId();
                            loginRespondDTO = new LoginRespondDTO(patientID, login.getLOGIN_Type());
                            return Response
                                    .ok(loginRespondDTO)
                                    .build();
                        }
                    }
                }
            }
            loginRespondDTO = new LoginRespondDTO(0, "");
            return Response
                    .ok(loginRespondDTO)
                    .header("Access-Control-Allow-Origin","*")
                    .header("Access-Control-Allow-Methods","GET , POST , DELETE , PUT")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
