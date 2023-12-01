package edu.oswego.cs.rest.JsonClasses;

public class Review extends JSession {
  private String movieTitle;
  private String movieId;
  private String reviewDescription;
  private String dateTimeCreated;
  private String privacy;
  private String username;

  public Review() {}

  public String getMovieTitle() {
    return movieTitle;
  }

  public void setMovieTitle(String movieTitle) {
    this.movieTitle = movieTitle;
  }

  public String getReviewDescription() {
    return reviewDescription;
  }

  public void setReviewDescription(String reviewDescription) {
    this.reviewDescription = reviewDescription;
  }

  public void setMovieId(String movieId) {
    this.movieId = movieId;
  }

  public String getMovieId() {
    return this.movieId;
  }

  public String getDateTimeCreated() { return dateTimeCreated; }

  public void setDateTimeCreated(String dateTimeCreated) { this.dateTimeCreated = dateTimeCreated;  }

  public String getPrivacy() { return privacy; }

  public void setPrivacy(String privacy) {
    this.privacy = privacy;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
