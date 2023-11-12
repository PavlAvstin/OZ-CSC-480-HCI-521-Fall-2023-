package edu.oswego.cs.rest.JsonClasses;

public class Rating extends JSession{
  private String movieTitle;
  private String ratingName;
  private String userRating;
  private String upperbound;
  private String dateTimeCreated;
  private String privacy;
  private String movieId;
  private String subtype;

  public Rating() {}

  public String getMovieTitle() {
    return movieTitle;
  }
  public void setMovieTitle(String movieTitle) {
    this.movieTitle = movieTitle;
  }

  public String getRatingName() {
    return ratingName;
  }
  public void setRatingName(String ratingName) {
    this.ratingName = ratingName;
  }

  public String getUserRating() {
    return userRating;
  }
  public void setUserRating(String userRating) {
    this.userRating = userRating;
  }

  public String getUpperbound() { return upperbound; }
  public void setUpperbound(String upperbound) { this.upperbound = upperbound; }

  public String getDateTimeCreated() { return dateTimeCreated;  }
  public void setDateTimeCreated(String dateTimeCreated) { this.dateTimeCreated = dateTimeCreated; }

  public String getPrivacy() { return privacy; }
  public void setPrivacy(String privacy) { this.privacy = privacy; }

  public String getMovieId() { return movieId; }
  public void setMovieId(String movieId) { this.movieId = movieId; }

  public String getSubtype() {return subtype;}
  public void setSubtype(String subtype) { this.subtype = subtype; }
}
