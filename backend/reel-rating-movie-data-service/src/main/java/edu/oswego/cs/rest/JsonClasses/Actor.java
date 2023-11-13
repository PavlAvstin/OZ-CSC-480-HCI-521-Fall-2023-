package edu.oswego.cs.rest.JsonClasses;

import java.util.List;

public class Actor extends JSession {
  private String id;
  private String name;
  private String dateOfBirth;
  private List<String> movies;

  public Actor() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

  public List<String> getMovies() {
    return movies;
  }

  public void setMovies(List<String> movies) {
    this.movies = movies;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
