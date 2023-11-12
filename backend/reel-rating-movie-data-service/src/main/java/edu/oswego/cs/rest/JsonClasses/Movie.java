package edu.oswego.cs.rest.JsonClasses;

import java.util.ArrayList;

public class Movie extends JSession {
  private String id;
  private String title;
  private String director;
  private String releaseDate;
  private String runtime;
  private String writers;
  private String summary;
  private String principleCast;
  private String mostPopularRatingCategory;
  private String mostPopRatingAvg;
  private String mostPopRatingUpperbound;
  private ArrayList<String> attachedTags;


  // constructor
  public Movie() {}

  // getters and setters
  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

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

  public String getPrincipleCast() {
    return principleCast;
  }
  public void setPrincipleCast(String principleCast) {
    this.principleCast = principleCast;
  }

  public String getMostPopularRatingCategory() { return mostPopularRatingCategory; }
  public void setMostPopularRatingCategory(String mostPopularRatingCategory){ this.mostPopularRatingCategory = mostPopularRatingCategory; }

  public String getMostPopRatingAvg() { return mostPopRatingAvg; }
  public void setMostPopRatingAvg(String mostPopRatingAvg) { this.mostPopRatingAvg = mostPopRatingAvg; }

  public String getMostPopRatingUpperBound() { return mostPopRatingUpperbound; }
  public void setMostPopRatingUpperBound(String mostPopRatingUpperBound) { this.mostPopRatingUpperbound = mostPopRatingUpperBound; }

  public ArrayList<String> getAttachedTags() { return attachedTags; }

  public void setAttachedTags(ArrayList<String> attachedTags) { this.attachedTags = attachedTags; }
}