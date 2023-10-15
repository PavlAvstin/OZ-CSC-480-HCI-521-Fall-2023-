package edu.oswego.cs.rest.JsonClasses;

public class Tag {
    private String tagName;
    private String movieTitles;

    public Tag() {}

    public String getTagName() { return tagName; }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getMovieTitles() {
        return movieTitles;
    }

    public void setMovieTitles(String movieTitles) {
        this.movieTitles = movieTitles;
    }
}
