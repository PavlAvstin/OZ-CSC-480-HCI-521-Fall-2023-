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
  
  /**
   * Provides a way for the user to login into our system(Opening a window of time for their
   * session to generate JWTs). A User Json with a provided username and password is consumed.
   * If the username exists and the password is validated an Ok reponse is returned.
   * Otherwise an Unauthorized Response is returned.
   * @param request Contains the needed session id of the user.
   * @param user A Json Containing a String username and String password.
   * @return Either an Ok Reponse or Unauthorized Response.
   * @throws NoSuchAlgorithmException
   */
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

  /**
   * Provides a way for a new user to be registered. A User Json with an associated username and password
   * is consumed. If the username and password meet our specifications an Ok Reponse is returned. Otherwise
   * an Unauthorized Response is returned.
   * @param request An HttpServletRequest that contains the new users current session id.
   * @param user A Json containing a String username and String password.
   * @return Response (Either Ok or Unauthorized)
   * @throws NoSuchAlgorithmException
   */
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
