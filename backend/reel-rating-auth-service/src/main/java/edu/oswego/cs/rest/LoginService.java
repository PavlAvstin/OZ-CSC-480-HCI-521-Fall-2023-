package edu.oswego.cs.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@RequestScoped
@Path("/auth")
public class LoginService {
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/login")
  public Response login(@Context HttpServletRequest request, @QueryParam("username") String username, @QueryParam("password") String password) {
    DatabaseController db = new DatabaseController();
    if (db.checkIfUserExists(username)) {
      String sessionId = request.getSession().getId();
      String stateMessage = "logged in";
      return Response.ok(stateMessage).build();
    } else {
      return Response.status(Status.UNAUTHORIZED).build();
    }
  }
}
