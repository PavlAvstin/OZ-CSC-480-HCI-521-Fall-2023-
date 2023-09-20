package edu.oswego.cs.rest;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

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
  public Response login(@Context HttpServletRequest request, @QueryParam("username") String username, @QueryParam("password") String password) throws NoSuchAlgorithmException {
    DatabaseController db = new DatabaseController();
    if (db.checkIfUserExists(username)) {
      if (SecurityUtils.validatePassword(password, db.getPassword(username))) {
        String sessionId = request.getSession().getId();
        db.setUserSessionId(username, sessionId);
        String stateMessage = "logged in";
        return Response.ok(stateMessage).build();
      }
    }
    return Response.status(Status.UNAUTHORIZED).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/register")
  public Response registerUser(@Context HttpServletRequest request, @QueryParam("username") String username, @QueryParam("password") String password) throws NoSuchAlgorithmException {
    DatabaseController db = new DatabaseController();
    if (!db.checkIfUserExists(username)) {
      String encryptedPassword = SecurityUtils.generatePassword(password);
      String sessionId = request.getSession().getId();
      String dateTime = LocalDateTime.now().toString();
      db.createUser(username, encryptedPassword, sessionId, dateTime);
      String stateMessage = "Registered";
      return Response.ok(stateMessage).build();
    }
    return Response.status(Status.UNAUTHORIZED).build();
  }
}
