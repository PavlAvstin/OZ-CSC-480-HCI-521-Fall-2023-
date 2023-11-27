package edu.oswego.cs.rest.JsonClasses;


public class Tag extends JSession implements Comparable{
    private String tagName;
    private String movieTitle;
    private String movieId;
    private String username;
    private String privacy;
    private String dateTimeCreated;
    private String state;
    private String totalCount;

    // constructor
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    // getters and setters
    public Tag() {}
    public String getTagName() { return tagName; }

    public String getMovieTitle() {
        return movieTitle;
    }
    public void setMovieTitle(String movieTitles) {
        this.movieTitle = movieTitles;
    }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public String getPrivacy() {
        return privacy;
    }
    public void setPrivacy(String privacy) { this.privacy = privacy; }

    public String getDateTimeCreated() { return dateTimeCreated; }
    public void setDateTimeCreated(String dateTimeCreated) { this.dateTimeCreated = dateTimeCreated; }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getTotalCount() { return totalCount; }
    public void setTotalCount(String totalCount) { this.totalCount = totalCount; }

    // compare to method

    public String toString(){
        return tagName;
    }

    @Override
    public int compareTo(Object tag2) {
        if( tag2 instanceof Tag ) {
            return Integer.parseInt(this.totalCount) - Integer.parseInt(((Tag) tag2).getTotalCount());
        }
        else {
            return 0;
        }
    }
}
