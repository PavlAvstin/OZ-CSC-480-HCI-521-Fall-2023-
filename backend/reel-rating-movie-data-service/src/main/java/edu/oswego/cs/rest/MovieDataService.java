package edu.oswego.cs.rest;

import edu.oswego.cs.rest.JsonClasses.Movie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.ibm.websphere.security.jwt.JwtConsumer;

import edu.oswego.cs.rest.DatabaseController;

@Path("/")
public class MovieDataService {
  
  String AuthServiceUrl = System.getenv("AUTH_SERVICE_URL");

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/movie/create")
  public Response createMovieEndPoint(@Context HttpServletRequest request, Movie movie) throws Exception {
    Client authClient = ClientBuilder.newClient();
    WebTarget target = authClient.target(AuthServiceUrl + "/reel-rating-auth-service/jwt/generate/" + request.getSession().getId());
    Response response = target.request().get();
    String value = response.readEntity(String.class);
    JwtConsumer jwtConsumer = JwtConsumer.create("reel_rating_token");
    String username = jwtConsumer.createJwt(value)
               .getClaims()
               .getAllClaims().get("upn").toString();
    authClient.close();
    if (username != null) {
      DatabaseController db = new DatabaseController();
      db.createMovie(movie.getTitle(), "", movie.getDirector(), "", movie.getReleaseDate(), movie.getRuntime(), movie.getWriters(), movie.getSummary());
      return Response.ok().build();
    }
    return Response.status(Response.Status.UNAUTHORIZED).build();
  }


}
