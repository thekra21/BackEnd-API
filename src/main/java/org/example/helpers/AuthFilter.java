package org.example.helpers;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.example.DAO.DoctorsDAO;
import org.example.DAO.PatientsDAO;
import org.example.DTO.ErrorMessage;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

@Provider
public class AuthFilter implements ContainerRequestFilter {


    @Inject DoctorsDAO docdao;
    @Inject PatientsDAO padao;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(!requestContext.getUriInfo().getPath().contains("secures"))
            return;

        List<String> authHeaders = requestContext.getHeaders().get("Authorization");

        if (authHeaders != null && authHeaders.size() != 0) {
            String authHeader = authHeaders.get(0);
            authHeader = authHeader.replace("Basic ", "");
            authHeader = new String(Base64.getDecoder().decode(authHeader));
            StringTokenizer tokenizer = new StringTokenizer(authHeader, ":");
            String email = tokenizer.nextToken();
            String password = tokenizer.nextToken();

            try {
                if (docdao.DoctorLogin(email,password) != null){
                    return;
                } else if (padao.PatientsLogin(email,password)!= null) {
                    return;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        ErrorMessage err = new ErrorMessage();
        err.setErrorContent("Please login");
        err.setErrorCode(Response.Status.UNAUTHORIZED.getStatusCode());
        err.setDocumentationUrl("https://google.com");

        Response res = Response.status(Response.Status.UNAUTHORIZED)
                .entity(err)
                .build();


        requestContext.abortWith(res);
    }
}


