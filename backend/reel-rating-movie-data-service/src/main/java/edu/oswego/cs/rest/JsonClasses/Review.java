package edu.oswego.cs.rest.JsonClasses;

public class Review {
  private String movieTitle;
  private String movieId;
  private String reviewTitle;
  private String reviewDescription;

  public Review() {}

  public String getMovieTitle() {
    return movieTitle;
  }

  public void setMovieTitle(String movieTitle) {
    this.movieTitle = movieTitle;
  }

  public String getReviewTitle() {
    return reviewTitle;
  }

  public void setReviewTitle(String reviewTitle) {
    this.reviewTitle = reviewTitle;
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
}
