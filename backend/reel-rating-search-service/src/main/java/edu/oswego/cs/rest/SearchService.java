package edu.oswego.cs.rest;

import edu.oswego.cs.rest.JsonClasses.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ibm.websphere.security.jwt.JwtConsumer;

@Path("/")
@RequestScoped
public class SearchService {

  String AuthServiceUrl = System.getenv("AUTH_SERVICE_URL");

  /**
   * gets the username of the client request. Also authenticates the client using a JWT.
   * TODO double check if the above is correct
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
   * Movie Get Endpoints
   *
   * searchMovieByName
   *searchMovieByReleaseDate
   * searchMovieByDirector
   * searchMovieByCast
   *
   * searchMovieByTagName
   * searchMovieByRatingName
   * searchUsersByName
   *
   *
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/searchByMovieNameIndex/{name}")
  public Response searchByMovieName(@Context HttpServletRequest request, @PathParam("name") String name, JSession jsession) throws Exception {
   String sessionId = request.getRequestedSessionId();
   if (sessionId == null) sessionId = jsession.getJSESSIONID();
   String requesterUsername = getUsername(sessionId);
   if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.searchByMovieNameIndex(name);
    return Response.ok(movies).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/manualSearchByMovieName/{name}")
  public Response manualSearchByMovieName(@Context HttpServletRequest request, @PathParam("name") String name, JSession jsession) throws Exception {
   String sessionId = request.getRequestedSessionId();
   if (sessionId == null) sessionId = jsession.getJSESSIONID();
   String requesterUsername = getUsername(sessionId);
   if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.manualSearchByMovieName(name);
    return Response.ok(movies).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/manualSearchByMovieReleaseDate/{releaseDate}")
  public Response manualSearchByMovieReleaseDate(@Context HttpServletRequest request, @PathParam("releaseDate") String releaseDate, JSession jsession) throws Exception {
   String sessionId = request.getRequestedSessionId();
   if (sessionId == null) sessionId = jsession.getJSESSIONID();
   String requesterUsername = getUsername(sessionId);
   if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.manualSearchByMovieReleaseDate(releaseDate);
    return Response.ok(movies).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/manualSearchByMovieDirector/{director}")
  public Response manualSearchByMovieDirector(@Context HttpServletRequest request, @PathParam("director") String director, JSession jsession) throws Exception {
   String sessionId = request.getRequestedSessionId();
   if (sessionId == null) sessionId = jsession.getJSESSIONID();
   String requesterUsername = getUsername(sessionId);
   if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.manualSearchByMovieDirector(director);
    return Response.ok(movies).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/manualSearchByMovieCast/{actorName}")
  public Response manualSearchByMovieCast(@Context HttpServletRequest request, @PathParam("actorName") String actorName, JSession jsession) throws Exception {
   String sessionId = request.getRequestedSessionId();
   if (sessionId == null) sessionId = jsession.getJSESSIONID();
   String requesterUsername = getUsername(sessionId);
   if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.manualSearchByMovieCast(actorName);
    return Response.ok(movies).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/searchByActorNameIndex/{actorName}")
  public Response searchByActorNameIndex(@Context HttpServletRequest request, @PathParam("actorName") String actorName, JSession jsession) throws Exception {
   String sessionId = request.getRequestedSessionId();
   if (sessionId == null) sessionId = jsession.getJSESSIONID();
   String requesterUsername = getUsername(sessionId);
   if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.searchByMovieCastIndex(actorName);
    return Response.ok(movies).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/searchByRatingName/{ratingName}")
  public Response searchByRatingName(@Context HttpServletRequest request, @PathParam("ratingName") String ratingName, JSession jsession) throws Exception {
    String sessionId = request.getRequestedSessionId();
    if (sessionId == null) sessionId = jsession.getJSESSIONID();
    String requesterUsername = getUsername(sessionId);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.searchbyRatingName(ratingName);
    return Response.ok(movies).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/searchByTagName/{tagName}")
  public Response searchByTagName(@Context HttpServletRequest request, @PathParam("tagName") String tagName, JSession jsession) throws Exception {
    String sessionId = request.getRequestedSessionId();
    if (sessionId == null) sessionId = jsession.getJSESSIONID();
    String requesterUsername = getUsername(sessionId);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.searchByTagName(tagName);
    return Response.ok(movies).build();
  }

}