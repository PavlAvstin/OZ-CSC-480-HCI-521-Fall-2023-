package edu.oswego.cs.rest;

import edu.oswego.cs.rest.JsonClasses.Movie;
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
    db.createMovie(movie.getTitle(), "", movie.getDirector(), "", movie.getReleaseDate(), movie.getRuntime(), movie.getWriters(), movie.getSummary());
    return Response.ok().build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/movie/getByTitle/{title}")
  public List<Movie> getMoviesWithTitleEndPoint(@Context HttpServletRequest request, @PathParam("title") String title) throws Exception {
    DatabaseController dbc = new DatabaseController();
    List<Movie> movies = dbc.getMoviesWithTitle(title);
    return movies;
  }


}
