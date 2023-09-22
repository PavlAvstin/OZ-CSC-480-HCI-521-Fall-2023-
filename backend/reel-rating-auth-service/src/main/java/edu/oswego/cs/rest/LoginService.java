package edu.oswego.cs.rest;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
  public Response login(@Context HttpServletRequest request, User user) throws NoSuchAlgorithmException {
    DatabaseController db = new DatabaseController();
    String username = user.getUsername().toLowerCase();
    if (db.checkIfUserExists(username)) {
      if (SecurityUtils.validatePassword(user.getPassword(), db.getPassword(username))) {
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
  public Response registerUser(@Context HttpServletRequest request, User user) throws NoSuchAlgorithmException {
    DatabaseController db = new DatabaseController();
    String username = user.getUsername().toLowerCase();
    String password = user.getPassword();

    Pattern usernameLength = Pattern.compile("[\\w!\"#$%&'()*+,-./:;<=>?@\\[\\]\\^`\\{|\\}~]{2,15}");
    Matcher usernameMatcher = usernameLength.matcher(username);

    Pattern passwordLength = Pattern.compile("[\\w!\"#$%&'()*+,-./:;<=>?@\\[\\]\\^`\\{|\\}~]{8,}");
    Matcher passwordLengthMatcher = passwordLength.matcher(password);

    Pattern passwordSpecialCharacter = Pattern.compile(".*[!\"#$%&'()*+,-./:;<=>?@\\[\\]\\^`\\{|\\}~]{1,}.*");
    Matcher passwordSpecialMatcher = passwordSpecialCharacter.matcher(password);

    Pattern passwordNumberRequirement = Pattern.compile(".*\\d{1,}.*");
    Matcher passwordNumberMatcher = passwordNumberRequirement.matcher(password);

    // Confirm the username meets our requirements
    if (usernameMatcher.matches()) {
      // Confirm the password meets the requirements
      if (passwordLengthMatcher.matches() && passwordSpecialMatcher.matches() && passwordNumberMatcher.matches()) {
        if (!db.checkIfUserExists(username)) {
          String encryptedPassword = SecurityUtils.generatePassword(user.getPassword());
          String sessionId = request.getSession().getId();
          String dateTime = LocalDateTime.now().toString();
          db.createUser(username, encryptedPassword, sessionId, dateTime);
          String stateMessage = "Registered";
          return Response.ok(stateMessage).build();
        }
      }
    }
    return Response.status(Status.UNAUTHORIZED).build();
  }
}
