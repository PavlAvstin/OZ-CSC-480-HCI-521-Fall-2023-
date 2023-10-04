package edu.oswego.cs.rest;

import java.util.HashSet;
import java.util.Set;

import com.ibm.websphere.security.jwt.JwtBuilder;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/jwt")
public class JwtAuthService {
  
  String AUTH_SERVICE_URL = System.getenv("AUTH_SERVICE_URL");

  @GET
  @Path("/generate/{id}")
  public Response generateToken(@PathParam("id") String id) throws Exception {

    String username = "id";
    
    Set<String> roles = new HashSet<>();
    roles.add("user");

    String jwt = JwtBuilder.create("reel_rating_token")
      .claim("sub", "reel_rating")
      .claim("upn", username)
      .claim("groups", roles)
      .claim("aud", "reel-rating")
      .claim("iss", AUTH_SERVICE_URL)
      .buildJwt().compact();

      return Response.ok(jwt).build();
  }
  
}
