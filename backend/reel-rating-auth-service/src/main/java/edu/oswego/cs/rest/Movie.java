package edu.oswego.cs.rest;

public class Movie {
  private String title;
  private String director;
  private String releaseDate;
  private String runtime;
  private String writers;
  private String summary;

  public Movie() {}

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) { 
    this.title = title;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getRuntime() {
    return runtime;
  }

  public void setRuntime(String runtime) {
    this.runtime = runtime;
  }

  public String getWriters() {
    return writers;
  }

  public void setWriters(String writers) {
    this.writers = writers;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
}