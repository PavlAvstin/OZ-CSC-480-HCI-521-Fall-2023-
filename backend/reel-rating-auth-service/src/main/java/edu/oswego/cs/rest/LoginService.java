package edu.oswego.cs.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/auth")
public class LoginService {
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/login")
  public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
    String stateMessage = "logged in";
    return Response.ok(stateMessage).build();
  }
}
