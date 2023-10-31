package edu.oswego.cs.rest;

import com.ibm.websphere.security.jwt.JwtConsumer;

import edu.oswego.cs.rest.JsonClasses.Rating;
import edu.oswego.cs.rest.JsonClasses.Tag;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

import java.util.List;

@Path("/")
@RequestScoped
public class RatingDataService {
  
  String AuthServiceUrl = System.getenv("AUTH_SERVICE_URL");

  /**
   * gets the username of the client request. Also authenticates the client using a JWT.
   *
   * @param request
   * @return String representation of the username within the request
   * @throws Exception
   */
  public String getUsername(HttpServletRequest request) throws Exception {
    Client authClient = ClientBuilder.newClient();
    WebTarget target = authClient.target(AuthServiceUrl + "/reel-rating-auth-service/jwt/generate/" + request.getRequestedSessionId());
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
  @Path("/rating/create")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createRating(@Context HttpServletRequest request, Rating rating) throws Exception {
    String requesterUsername = getUsername(request);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    dbc.createRating(rating.getRatingName(), rating.getUserRating(), rating.getUpperbound(), requesterUsername, rating.getMovieId(), rating.getPrivacy());
    return Response.ok().build();
  }

  @GET
  @Path("/rating/getMostPopularAggregatedRatingForMovie/{movieId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMostPopularAggregatedRatingForMovie(@Context HttpServletRequest request, @PathParam("movieId") String movieId) throws Exception {
    String requesterUsername = getUsername(request);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    Rating aggregatedRating = dbc.getMostPopularAggregatedRatingForMovie(movieId);
    return Response.ok(aggregatedRating).build();
  }

  @GET
  @Path("/rating/getRatingsWithSameNameAndUpperbound/{ratingName}{upperbound}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRatingsWithSameNameAndUpperbound(@Context HttpServletRequest request, @PathParam("ratingName") String ratingName, @PathParam("upperbound") String upperbound ) throws Exception {
    String requesterUsername = getUsername(request);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Rating> ratings = dbc.getRatingsWithSameNameAndUpperbound(ratingName, upperbound);
    return Response.ok(ratings).build();
  }

  @GET
  @Path("/rating/getRatingsWithSameName/{ratingName}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRatingsWithSameName(@Context HttpServletRequest request, @PathParam("ratingName") String ratingName) throws Exception {
    String requesterUsername = getUsername(request);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Rating> ratings = dbc.getRatingsWithSameName(ratingName);
    return Response.ok(ratings).build();
  }

  @GET
  @Path("/rating/getRatingsWithMovieId/{movieId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRatingsWithMovieId(@Context HttpServletRequest request, @PathParam("movieId") String movieId) throws Exception {
    String requesterUsername = getUsername(request);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Rating> ratings = dbc.getRatingsWithMovieId(movieId);
    return Response.ok(ratings).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/tag/create/{movieId}")
  public Response createTag(@Context HttpServletRequest request, Tag tag, @PathParam("movieId") String movieId) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController db = new DatabaseController();
    db.createTag(tag.getTagName(), movieId, username, tag.getPrivacy());
    return Response.ok().build();
  }

  @GET
  @Path("/tag/getTagsByMovieId/{movieId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTagsWithMovieId(@Context HttpServletRequest request, @PathParam("movieId") String movieId) throws Exception {
    String requesterUsername = getUsername(request);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Tag> tags = dbc.getTagsWithMovieId(movieId);
    return Response.ok(tags).build();
  }
}
