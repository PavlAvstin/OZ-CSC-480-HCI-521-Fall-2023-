package edu.oswego.cs.rest.JsonClasses;

public class Actor {
  private String id;
  private String name;
  private String dateOfBirth;
  private String movies;

  public Actor() {}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

  public String getMovies() {
    return movies;
  }

  public void setMovies(String movies) {
    this.movies = movies;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
