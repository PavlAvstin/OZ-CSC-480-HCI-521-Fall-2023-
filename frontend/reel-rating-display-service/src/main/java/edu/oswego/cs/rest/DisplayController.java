package edu.oswego.cs.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RequestScoped
@Path("/")
public class DisplayController {
  
  @GET
  @Path("/")
  @Produces(MediaType.TEXT_HTML)
  public String displayLogin() {
    return "";
  }
}
