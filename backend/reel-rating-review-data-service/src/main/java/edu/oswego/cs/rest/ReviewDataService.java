package edu.oswego.cs.rest;

import com.ibm.websphere.security.jwt.JwtConsumer;

import edu.oswego.cs.rest.JsonClasses.JSession;
import edu.oswego.cs.rest.JsonClasses.Review;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/")
@RequestScoped
public class ReviewDataService {
  
  String AuthServiceUrl = System.getenv("AUTH_SERVICE_URL");

  /**
   * gets the username of the client request. Also authenticates the client using a JWT.
   *
   * @param sessionId
   * @return String representation of the username within the request
   * @throws Exception
   */
  public String getUsername(String sessionId) throws Exception {
    Client authClient = ClientBuilder.newClient();
    WebTarget target = authClient.target(AuthServiceUrl + "/reel-rating-auth-service/jwt/generate/" + sessionId);
    Response response = target.request().get();
    String value = response.readEntity(String.class);
    if (value == null || value == "") {
       return null;
    }
    JwtConsumer jwtConsumer = JwtConsumer.create("reel_rating_token");
    String username = jwtConsumer.createJwt(value)
               .getClaims()
               .getAllClaims().get("upn").toString();
    authClient.close();
    return username;
  }

  /*
   * Review Create Endpoints
   *
   * createReview
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/review/create/{movieId}")
  public Response createReview(@Context HttpServletRequest request, Review review, @PathParam("movieId") String movieId) throws Exception {
    String sessionId = request.getRequestedSessionId();
    if (sessionId == null) sessionId = review.getJSESSIONID();
    String requesterUsername = getUsername(sessionId);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController db = new DatabaseController();
    db.createReview(movieId, review.getReviewDescription(), requesterUsername.toLowerCase(), review.getPrivacy());
    return Response.ok().build();
  }

  /*
   * Review Get Endpoints
   *
   * getReviewsWithUsername
   * getReviewsWithMovieId
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/reviews/getReviewsWithUsername/{username}")
  public Response getReviewsWithUsername(@Context HttpServletRequest request, @PathParam("username") String username, JSession jsession) throws Exception {
    String sessionId = request.getRequestedSessionId();
    if (sessionId == null) sessionId = jsession.getJSESSIONID();
    String requesterUsername = getUsername(sessionId);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Review> reviews = dbc.getReviewsWithUsername(username.toLowerCase());
    return Response.ok(reviews).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/reviews/getReviewsWithMovieId/{movieId}")
  public Response getReviewsWithMovieId(@Context HttpServletRequest request, @PathParam("movieId") String movieId, JSession jsession) throws Exception {
    String sessionId = request.getRequestedSessionId();
    if (sessionId == null) sessionId = jsession.getJSESSIONID();
    String requesterUsername = getUsername(sessionId);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Review> reviews = dbc.getReviewsWithMovieId(movieId);
    return Response.ok(reviews).build();
  }
}
