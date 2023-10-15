package edu.oswego.cs.rest;

import edu.oswego.cs.rest.JsonClasses.Actor;
import edu.oswego.cs.rest.JsonClasses.Tag;
import edu.oswego.cs.rest.JsonClasses.Movie;
import edu.oswego.cs.rest.JsonClasses.Review;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.ibm.websphere.security.jwt.JwtConsumer;

import edu.oswego.cs.rest.DatabaseController;

@Path("/")
@RequestScoped
public class MovieDataService {
  
  String AuthServiceUrl = System.getenv("AUTH_SERVICE_URL");

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

  /**
   * Create Endpoints
   */

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/movie/create")
  public Response createMovieEndPoint(@Context HttpServletRequest request, Movie movie) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController db = new DatabaseController();
    db.createMovie(movie.getTitle(), movie.getDirector(), movie.getReleaseDate(), movie.getRuntime(), movie.getWriters(), movie.getSummary());
    return Response.ok().build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/actor/create/{movieTitle}")
  public Response createActorEndPoint(@Context HttpServletRequest request, Actor actor, @PathParam("movieTitle") String movieTitle) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController db = new DatabaseController();
    db.createActor(actor.getName(), actor.getDateOfBirth(), movieTitle);
    return Response.ok().build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/tag/create/{movieId}")
  public Response createFlagEndPoint(@Context HttpServletRequest request, Tag tag, @PathParam("movieId") String movieId) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController db = new DatabaseController();
    db.createTag(tag.getTagName(), movieId);
    return Response.ok().build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/review/create/{movieId}")
  public Response createReviewEndPoint(@Context HttpServletRequest request, Review review, @PathParam("movieId") String movieId) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController db = new DatabaseController();
    String tempUsername = "TempUsername";
    db.createReview(movieId, review.getReviewDescription(), tempUsername, review.getPrivacy());
    return Response.ok().build();
  }

  /**
   * get endpoints for movies
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/getByTitle/{title}")
  public Response getMoviesWithTitleEndPoint(@Context HttpServletRequest request, @PathParam("title") String title) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithTitle(title);
    return Response.ok(movies).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/getByTagName/{TagName}")
  public Response getMoviesWithTagName(@Context HttpServletRequest request, @PathParam("TagName") String tagName) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithTag(tagName);
    return Response.ok(movies).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/getByRatingCategoryName/{ratingCategoryName}")
  public Response getMoviesWithRatingCategoryName(@Context HttpServletRequest request, @PathParam("ratingCategoryName") String ratingCategoryName) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithRatingCategory(ratingCategoryName);
    return Response.ok(movies).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/getByActor/{actor}")
  public Response getMoviesWithActor(@Context HttpServletRequest request, @PathParam("actor") String actor) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithActor(actor);
    return Response.ok(movies).build();
  }

  /**
   * get endpoints for Actors
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/actor/getByName/{name}")
  public Response getActorByName(@Context HttpServletRequest request, @PathParam("name") String name) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }  
    DatabaseController dbc = new DatabaseController();
    List<Actor> actors = dbc.getActorByName(name);
    return Response.ok(actors).build();
  }

  /**
   * get endpoints for reviews
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/reviews/getByUser/{username}")
  public Response getReviewsByUser(@Context HttpServletRequest request, @PathParam("username") String username) throws Exception {
    String requesterUsername = getUsername(request);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }      
    DatabaseController dbc = new DatabaseController();
    List<Review> reviews = dbc.getReviewsByUser(username);
    return Response.ok(reviews).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/reviews/getByMovieId/{movieId}")
  public Response getReviewsByMovieId(@Context HttpServletRequest request, @PathParam("movieId") String movieId) throws Exception {
    String requesterUsername = getUsername(request);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); } 
    DatabaseController dbc = new DatabaseController();
    List<Review> reviews = dbc.getReviewsByMovieId(movieId);
    return Response.ok(reviews).build();
  }

  /**
   * Image methods
   */

  /**
   *
   * @param request TODO describe what this is
   * @param movieId unique MongoDB id for a movie
   * @return stock image from the pre-populated database collection
   * @throws Exception TODO not sure if that is needed
   */
  @GET
  @Produces("image/jpg")
  @Path("/movie/getMovieImage/{movieId}")
  public byte[] getMovieImage(@Context HttpServletRequest request, @PathParam("movieId") String movieId) throws Exception {
    DatabaseController dbc = new DatabaseController();
    String imageId = dbc.getMovieImageId(movieId);
    return dbc.getStockImage(imageId);
  }

  /**
   * Populates the database on startup with pre-selected stock images. This must be run otherwise the createMovies
   * functionality will not work.
   */
  @POST
  @Path("/stockImages/generate")
  public void generateStockImages() {
    DatabaseController dbc = new DatabaseController();
    dbc.storeStockImages();
  }
}