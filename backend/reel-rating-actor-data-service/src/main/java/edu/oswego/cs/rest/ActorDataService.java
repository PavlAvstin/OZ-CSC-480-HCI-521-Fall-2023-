package edu.oswego.cs.rest;

import com.ibm.websphere.security.jwt.JwtConsumer;

import edu.oswego.cs.rest.JsonClasses.Actor;
import edu.oswego.cs.rest.JsonClasses.JSession;
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
public class ActorDataService {
  
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

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/actor/create/{movieId}")
  public Response createActorEndPoint(@Context HttpServletRequest request, Actor actor, @PathParam("movieId") String movieId) throws Exception {
    String sessionId = request.getRequestedSessionId();
    if (sessionId == null) sessionId = actor.getJSESSIONID();
    String requesterUsername = getUsername(sessionId);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController db = new DatabaseController();
    db.createActor(actor.getName(), actor.getDateOfBirth(), movieId);
    return Response.ok().build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/actor/getActorWithName/{name}")
  public Response getActorWithName(@Context HttpServletRequest request, @PathParam("name") String name, JSession jsession) throws Exception {
    String sessionId = request.getRequestedSessionId();
    if (sessionId == null) sessionId = jsession.getJSESSIONID();
    String requesterUsername = getUsername(sessionId);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Actor> actors = dbc.getActorWithName(name);
    return Response.ok(actors).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/actor/getActorsWithMovieId/{movieId}")
  public Response getActorsByMovieId(@Context HttpServletRequest request, @PathParam("movieId") String movieId, JSession jsession) throws Exception {
    String sessionId = request.getRequestedSessionId();
    if (sessionId == null) sessionId = jsession.getJSESSIONID();
    String requesterUsername = getUsername(sessionId);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Actor> actors = dbc.getActorWithMovieId(movieId);
    return Response.ok(actors).build();
  }
}
