package edu.oswego.cs.rest;

import edu.oswego.cs.rest.JsonClasses.Actor;
import edu.oswego.cs.rest.JsonClasses.Flag;
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
    WebTarget target = authClient.target(AuthServiceUrl + "/reel-rating-auth-service/jwt/generate/" + request.getSession().getId());
    Response response = target.request().get();
    String value = response.readEntity(String.class);
    // if (value == null) {
    //   throw new Exception();
    // }
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
    // try {
    //   String username = getUsername(request);
    // } catch (Exception e) {
    //   return Response.status(Response.Status.UNAUTHORIZED).build();
    // }
    DatabaseController db = new DatabaseController();
    db.createMovie(movie.getTitle(), movie.getDirector(), movie.getReleaseDate(), movie.getRuntime(), movie.getWriters(), movie.getSummary());
    return Response.ok().build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/actor/create/{movieTitle}")
  public Response createActorEndPoint(@Context HttpServletRequest request, Actor actor, @PathParam("movieTitle") String movieTitle) throws Exception {
    // try {
    //   String username = getUsername(request);
    // } catch (Exception e) {
    //   return Response.status(Response.Status.UNAUTHORIZED).build();
    // }
    DatabaseController db = new DatabaseController();
    db.createActor(actor.getName(), actor.getDateOfBirth(), movieTitle);
    return Response.ok().build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/flag/create/{movieId}")
  public Response createFlagEndPoint(@Context HttpServletRequest request, Flag flag, @PathParam("movieId") String movieId) throws Exception {
    // try {
    //   String username = getUsername(request);
    // } catch (Exception e) {
    //   return Response.status(Response.Status.UNAUTHORIZED).build();
    // }
    DatabaseController db = new DatabaseController();
    db.createFlag(flag.getFlagName(), movieId);
    return Response.ok().build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/review/create/{movieId}")
  public Response createReviewEndPoint(@Context HttpServletRequest request, Review review, @PathParam("movieId") String movieId) throws Exception {
    // try {
    //   String username = getUsername(request);
    // } catch (Exception e) {
    //   return Response.status(Response.Status.UNAUTHORIZED).build();
    // }
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
  public List<Movie> getMoviesWithTitleEndPoint(@Context HttpServletRequest request, @PathParam("title") String title) throws Exception {
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithTitle(title);
    return movies;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/getByFlagName/{flagName}")
  public List<Movie> getMoviesWithFlagName(@Context HttpServletRequest request, @PathParam("flagName") String flagName) throws Exception {
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithFlag(flagName);
    return movies;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/getByRatingCategoryName/{ratingCategoryName}")
  public List<Movie> getMoviesWithRatingCategoryName(@Context HttpServletRequest request, @PathParam("ratingCategoryName") String ratingCategoryName) throws Exception {
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithRatingCategory(ratingCategoryName);
    return movies;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/getByActor/{actor}")
  public List<Movie> getMoviesWithActor(@Context HttpServletRequest request, @PathParam("actor") String actor) throws Exception {
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithActor(actor);
    return movies;
  }

  /**
   * get endpoints for Actors
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/actor/getByName/{name}")
  public List<Actor> getActorByName(@Context HttpServletRequest request, @PathParam("name") String name) throws Exception {
    DatabaseController dbc = new DatabaseController();
    List<Actor> actors = dbc.getActorByName(name);
    return actors;
  }

  /**
   * get endpoints for reviews
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/reviews/getByUser/{username}")
  public List<Review> getReviewsByUser(@Context HttpServletRequest request, @PathParam("username") String username) throws Exception {
    DatabaseController dbc = new DatabaseController();
    List<Review> reviews = dbc.getReviewsByUser(username);
    return reviews;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/reviews/getByMovieId/{movieId}")
  public List<Review> getReviewsByMovieId(@Context HttpServletRequest request, @PathParam("movieId") String movieId) throws Exception {
    DatabaseController dbc = new DatabaseController();
    List<Review> reviews = dbc.getReviewsByMovieId(movieId);
    return reviews;
  }

  @GET
  @Produces("image/jpg")
  @Path("/movie/getMovieImage/{movieId}")
  public byte[] getMovieImage(@Context HttpServletRequest request, @PathParam("movieId") String movieId) throws Exception {
    DatabaseController dbc = new DatabaseController();
    String imageId = dbc.getMovieImageId(movieId);
    return dbc.getStockImage(imageId);
  }

  @POST
  @Path("/stockImages/generate")
  public void generateStockImages() {
    DatabaseController dbc = new DatabaseController();
    dbc.storeStockImages();
  }
}
