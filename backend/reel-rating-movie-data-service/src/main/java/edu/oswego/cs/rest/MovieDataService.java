package edu.oswego.cs.rest;

import edu.oswego.cs.rest.JsonClasses.Actor;
import edu.oswego.cs.rest.JsonClasses.Tag;
import edu.oswego.cs.rest.JsonClasses.Movie;
import edu.oswego.cs.rest.JsonClasses.Rating;
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
import java.util.List;

import com.ibm.websphere.security.jwt.JwtConsumer;
import org.bson.BsonDocument;
import org.bson.BsonValue;

@Path("/")
@RequestScoped
public class MovieDataService {
  
  String AuthServiceUrl = System.getenv("AUTH_SERVICE_URL");

  /**
   * gets the username of the client request. Also authenticates the client using a JWT.
   * TODO double check if the above is correct
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
  @Path("/actor/create/{movieId}")
  public Response createActorEndPoint(@Context HttpServletRequest request, Actor actor, @PathParam("movieId") String movieId) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController db = new DatabaseController();
    db.createActor(actor.getName(), actor.getDateOfBirth(), movieId);
    return Response.ok().build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/tag/create/{movieId}")
  public Response createTagEndPoint(@Context HttpServletRequest request, Tag tag, @PathParam("movieId") String movieId) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController db = new DatabaseController();
    db.createTag(tag.getTagName(), movieId, username, tag.getPrivacy());
    return Response.ok().build();
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

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/review/create/{movieId}")
  public Response createReviewEndPoint(@Context HttpServletRequest request, Review review, @PathParam("movieId") String movieId) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController db = new DatabaseController();
    db.createReview(movieId, review.getReviewDescription(), username, review.getPrivacy());
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
  @Path("/movie/getByActor/{actorId}")
  public Response getMoviesWithActorId(@Context HttpServletRequest request, @PathParam("actorId") String actorId) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithActor(actorId);
    return Response.ok(movies).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/getMoviesWithMostReviews")
  public Response getMoviesWithMostReviews(@Context HttpServletRequest request) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    int numMovies = 12;
    List<Movie> movies = dbc.getMoviesWithMostReviews(numMovies);
    return Response.ok(movies).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/getRecentReleaseMovies")
  public Response getRecentReleaseMoviesEndpoint(@Context HttpServletRequest request) throws Exception {
    String username = getUsername(request);
    if (username == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    int numMovies = 12;
    // get a List of the #numMovies most recent releases.
    List<Movie> movies = dbc.getRecentReleaseMovies(numMovies);
    for ( Movie m : movies ) {
      // get the most popular rating and average for each movie
      Rating r = dbc.getMostPopularAggregatedRatingForMovie(m.getId());

      // set the appropriate fields for each movie
      m.setMostPopularRatingCategory(r.getRatingName());
      m.setMostPopRatingUpperBound(r.getUpperbound());
      m.setMostPopRatingAvg(r.getUserRating());

      // names of three tags from the movie
      List<Tag> tagList = dbc.getThreeTags(m.getId());
      ArrayList<String> tagNameList = new ArrayList<>();
      for (Tag t: tagList ) {
        tagNameList.add(t.getTagName());
      }
      m.setAttachedTags(tagNameList);
    }
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
    List<Review> reviews = dbc.getReviewsByUser(username.toLowerCase());
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
   * Takes a rating name and rating upperbound in order to find the rating category.
   * @param request
   * @param rating
   * @return A list of ratings that are within the rating category.
   * @throws Exception
   */
  @POST
  @Path("/rating/getRatingsInRatingCategory")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRatingsInRatingCategory(@Context HttpServletRequest request, Rating rating) throws Exception {
    String requesterUsername = getUsername(request);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Rating> ratings = dbc.getRatingsInRatingsCategory(rating.getRatingName(), rating.getUpperbound());
    return Response.ok(ratings).build();
  }

  /**
   * Takes a rating name and rating upperbound in order to find the rating category.
   * @param request
   * @param rating
   * @return A list of movies that are within the rating category.
   * @throws Exception
   */
  @POST
  @Path("/movie/getByRatingCategory")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMoviesByRatingCategory(@Context HttpServletRequest request, Rating rating) throws Exception {
    String requesterUsername = getUsername(request);
    if (requesterUsername == null) { return Response.status(Response.Status.UNAUTHORIZED).build(); }
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithRatingCategory(rating.getRatingName(), rating.getUpperbound());
    return Response.ok(movies).build();
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
}