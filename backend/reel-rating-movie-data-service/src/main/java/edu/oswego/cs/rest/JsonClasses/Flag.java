package edu.oswego.cs.rest.JsonClasses;

public class Flag {
    private String flagName;
    private String movieTitles;

    public Flag() {}

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }

    public String getMovieTitles() {
        return movieTitles;
    }

    public void setMovieTitles(String movieTitles) {
        this.movieTitles = movieTitles;
    }
}
