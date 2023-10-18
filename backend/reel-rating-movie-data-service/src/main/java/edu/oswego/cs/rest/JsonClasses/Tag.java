package edu.oswego.cs.rest.JsonClasses;

public class Tag {
    private String tagName;
    private String movieTitle;
    private String username;
    private String privacy;
    private String dateTimeCreated;

    public String getTagName() { return tagName; }

    // getters and setters
    public Tag() {}

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getMovieTitles() {
        return movieTitle;
    }

    public void setMovieTitles(String movieTitles) {
        this.movieTitle = movieTitles;
    }

    public String getPrivacy() {
        return privacy;
    }
}
