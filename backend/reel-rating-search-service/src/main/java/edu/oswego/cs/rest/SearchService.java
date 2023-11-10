package edu.oswego.cs.rest;

import com.ibm.websphere.security.jwt.JwtConsumer;

import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

@Path("/")
@RequestScoped
public class SearchService {
  
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
}
