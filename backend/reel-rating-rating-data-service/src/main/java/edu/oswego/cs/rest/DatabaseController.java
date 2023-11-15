package edu.oswego.cs.rest;

import com.mongodb.client.model.*;
import edu.oswego.cs.rest.JsonClasses.Rating;
import edu.oswego.cs.rest.JsonClasses.Tag;
import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseController {
  private static String mongoDatabaseName = System.getenv("MONGO_MOVIE_DATABASE_NAME");
  private static String mongoURL = System.getenv("MONGO_MOVIE_URL");
  private static MongoClient mongoClient = MongoClients.create(mongoURL);

  /*
   *
   */
  public MongoDatabase getMovieDatabase() {
    return mongoClient.getDatabase(mongoDatabaseName);
  }

  /*
   * Database get collection methods
   */
  public MongoCollection<Document> getMovieCollection() {
    return getMovieDatabase().getCollection("movies");
  }

  public MongoCollection<Document> getRatingCollection() {
    return getMovieDatabase().getCollection("ratings");
  }

  public MongoCollection<Document> getTagCollection() {
    return getMovieDatabase().getCollection("tags");
  }

  /*
   * Rating Create functions
   *
   * createRating
   */
    /**
   * Creates and adds a rating object associated with a movie to the database. Employs a series
   * of checks to make sure the movie exists and the ratingCategory does not already exist.
   * @param ratingName Name of the rating category. For example, "How Harrison Ford is it", "Stickiness"
   * @param movieIdHexString movie unique MongoDB identifier
   * @param username user to associate with the rating
   * @param userRating value assigned by the user
   * @param upperbound upperbound of the rating scale. 0 < upperbound < 11
   */
  public void createRating(String ratingName, String userRating, String upperbound, String subtype, String username,
                           String movieIdHexString, String privacy){
    // get collections
    MongoCollection<Document> ratingCollection = getRatingCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();

    // check if the user rating is between 1 and the upperbound
    if (!(Integer.valueOf(userRating) <= Integer.valueOf(upperbound) && Integer.valueOf(userRating) >= 1))
      return;

    // attempt to get the rating if the user has already created one for this category and upperbound on the movie
    Bson upperBoundFilter = Filters.eq("upperbound", upperbound);
    Bson ratingNameFilter = Filters.eq("ratingName", ratingName);
    Bson usernameFilter = Filters.eq("username", username);
    Bson movieFilter = Filters.eq("movieId", movieIdHexString);
    Document rating = ratingCollection.find(Filters.and(usernameFilter, ratingNameFilter, upperBoundFilter, movieFilter)).first();
    // attempt to get the corresponding movie
    Document movie = getMovieDocumentWithHexId(movieIdHexString);

    // check to see if movie exists and rating is not already created by user
    if (rating == null && movie != null) {
      Document newRating = new Document("userName", username)
                  .append("ratingName", ratingName)
                  .append("userRating", userRating)
                  .append("upperbound", upperbound)
                  .append("movieTitle", movie.get("title"))
                  .append("movieId", movieIdHexString)
                  .append("dateTimeCreated", new BsonDateTime(System.currentTimeMillis()))
                  .append("privacy", privacy)
                  .append("subtype", subtype);
      ratingCollection.insertOne(newRating);

      Bson ratingCategoryMovieFilter = Filters.eq("ratingCategoryNames", ratingName);
      ObjectId movieId = new ObjectId(movieIdHexString);
      Bson movieIdFilter = Filters.eq("_id", movieId);
      Document movieWithRatingCategory = movieCollection.find(Filters.and(ratingCategoryMovieFilter, movieIdFilter)).first();
      // check to see if movie category needs to be pushed
      if (movieWithRatingCategory == null) {
        Bson movieRatingCategoryUpdateOperation = Updates.push("ratingCategoryNames", ratingName);
        movieCollection.updateOne(movie, movieRatingCategoryUpdateOperation);
      }
    }
  }

  /*
   * Rating Get methods
   *
   * getRatingsWithFilter
   *
   * getRatingsWithSameNameAndUpperbound
   * getRatingsWithSameName
   * getRatingsWithMovieId
   * getRatingsWithUpperbound
   * getMostPopularAggregatedRatingForMovie
   */

  /**
   * Creates and returns a list of ratings that match the given filter. This is called by many of the
   * other get functions.
   * @param ratingsCollection
   * @param filter
   * @return
   */
  private static ArrayList<Rating> getRatingsWithFilter(MongoCollection<Document> ratingsCollection, Bson filter) {
    var ratings = ratingsCollection.find(filter).map(document -> {
      var ra = new Rating();
      ra.setRatingName(document.getString("ratingName"));
      ra.setUserRating(document.getString("userRating"));
      ra.setMovieTitle(document.getString("movieTitle"));
      ra.setDateTimeCreated(document.get("dateTimeCreated").toString());
      ra.setPrivacy(document.getString("privacy"));
      ra.setMovieId(document.getString("movieId"));
      ra.setUpperbound(document.getString("upperbound"));
      ra.setSubtype(document.getString("subtype"));
      return ra;
    });
    var list = new ArrayList<Rating>();
    ratings.forEach(list::add);
    return list;
  }

  public List<Rating> getRatingsWithSameNameAndUpperbound(String ratingName, String upperbound) {
    var ratings = getRatingCollection();
    Bson filter = Filters.and(
            Filters.eq("ratingName", ratingName),
            Filters.eq("upperbound", upperbound));
    return getRatingsWithFilter(ratings, filter);
  }

  public List<Rating> getRatingsWithSameName(String ratingName) {
    var ratings = getRatingCollection();
    var ratingNameFilter = Filters.eq("ratingName", ratingName);
    return getRatingsWithFilter(ratings, ratingNameFilter);
  }

  public List<Rating> getRatingsWithMovieId(String movieId){
    var ratings = getRatingCollection();
    var movieIdFilter = Filters.eq("movieId", movieId);
    return getRatingsWithFilter(ratings, movieIdFilter);
  }

  public List<Rating> getRatingsWithUpperbound(String upperbound){
    var ratings = getRatingCollection();
    var upperboundFilter = Filters.eq("upperbound", upperbound);
    return getRatingsWithFilter(ratings, upperboundFilter);
  }

  /**
   * Finds the most popular upperbound of the most popular rating name and calculates the average
   * of the userRatings
   * @param movieId MongoDB HexId of the movie to search for ratings from
   * @return A Rating object with the most popular category name, an upperbound of the most common
   * upperbound, and a userRating of the average of all userRatings for the category and upperbound.
   */
  public Rating getMostPopularAggregatedRatingForMovie(String movieId) {
    MongoCollection<Document> ratingCollection = getRatingCollection();
    // get the most popular rating category name for the movie
    Document ratingNameDoc = ratingCollection.aggregate(
            Arrays.asList(
                    Aggregates.match(Filters.eq("movieId", movieId)),
                    Aggregates.group("$ratingName", Accumulators.sum("count", 1)),
                    Aggregates.sort(Sorts.descending("count"))
            )
    ).first();

    // gets the most popular upperbound for the category
    String mostPopularCategoryName = ratingNameDoc.getString("_id");
    Document ratingScaleDoc = ratingCollection.aggregate(
            Arrays.asList(
                    Aggregates.match(Filters.and(Filters.eq("movieId", movieId), Filters.eq("ratingName", mostPopularCategoryName))),
                    Aggregates.group("$upperbound", Accumulators.sum("count", 1)),
                    Aggregates.sort(Sorts.descending("count"))
            )
    ).first();
    String mostPopularCategoryUpperbound = ratingScaleDoc.getString("_id");

    int userRatingSum = 0;
    // using the most popular name and most popular upperbound go through and collect the sum of all the user ratings
    // gets the most popular upperbound for the category
    for (Document doc : ratingCollection.find(Filters.and(Filters.eq("movieId", movieId), Filters.eq("ratingName", mostPopularCategoryName), Filters.eq("upperbound", mostPopularCategoryUpperbound)))) {
      userRatingSum = userRatingSum + Integer.parseInt(doc.getString("userRating"));
    }
    int count = ratingScaleDoc.getInteger("count");
    double average = ((double) userRatingSum ) / count;

    // create a rating object that has the most popular name, upperbound, and a userRating of the average of all
    //  the ratings of that name with that upperbound.
    //  TODO consider collecting for all ratings of this name which would take some normalizing. Is this worth it?
    Rating rating = new Rating();
    rating.setRatingName(mostPopularCategoryName);
    rating.setUpperbound(mostPopularCategoryUpperbound);
    rating.setUserRating(Double.toString(average));
    return rating;
  }

  /*
   * Rating Update functions
   */

  /*
   * Rating Delete functions
   */




  /*
   * Tag Create functions
   *
   * createTags
   */
  /**
   * Users are not allowed to create a tag for a movie that does not already exist, or the same tag for the same movie.
   * Otherwise, duplicate tags are allowed by multiple users due to privacy issues
   *
   * @param tagName name of tag used to access and store the information
   * @param movieIdHexString MongoDB unique identifier for the movie to attach the tag to
   * @param username name of the user trying to create the tag
   * @param privacy privacy setting of the tag whether it is private, friends-only, or public
   */
  public void createTag(String tagName, String movieIdHexString, String username, String privacy){
    // get the collections
    MongoCollection<Document> tagCollection = getTagCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();

    // attempt to grab the movie using its unique MongoDB id
    ObjectId movieId = new ObjectId(movieIdHexString);
    Document movie = movieCollection.find(Filters.eq("_id", movieId)).first();

    // if the movie does not exist move on
    if (movie == null) { return; }

    // attempt to grab the tag in a few different types
    Bson movieTitleFilter = Filters.eq("movieTitle", movie.get("title"));
    Bson usernameFilter = Filters.eq("username", username.toLowerCase());
    Bson tagNameFilter = Filters.eq("tagName", tagName);

    Document taggedWithMovieByUser = tagCollection.find(Filters.and(movieTitleFilter,usernameFilter, tagNameFilter)).first();

    // if you have already tagged this movie this tag
    if(taggedWithMovieByUser != null){

    } // else the tag does not exist
    else{
      // create and add the tag
      Document newTag = new Document("username", username.toLowerCase())
              .append("tagName", tagName)
              .append("movieTitle", movie.get("title"))
              .append("movieId", movieIdHexString)
              .append("dateTimeCreated", new BsonDateTime(System.currentTimeMillis()))
              .append("privacy", privacy)
              .append("state", "upvote");
      // add to the database
      tagCollection.insertOne(newTag);

      Bson tagMovieFilter = Filters.eq("tagNames", tagName);
      Bson movieIdFilter = Filters.eq("_id", movieId);
      Document movieWithTag = movieCollection.find(Filters.and(tagMovieFilter, movieIdFilter)).first();
      // check to see if movie category needs to be pushed
      if (movieWithTag == null) {
        Bson movieRatingCategoryUpdateOperation = Updates.push("tagNames", tagName);
        movieCollection.updateOne(movie, movieRatingCategoryUpdateOperation);
      }
    }

  }

  /*
   * Tag Get functions
   *
   * getTagsWithFilter
   * getTagsByMovieId
   * getTagsWithTagName
   * getTagsWithUsername
   */

  /**
   * Creates and returns a list of ratings that match the given filter. This is called by many of the other
   * get methods for tags.
   * @param tagCollection
   * @param filter
   * @return
   */
  private static ArrayList<Tag> getTagsWithFilter(MongoCollection<Document> tagCollection, Bson filter) {
    var ratings = tagCollection.find(filter).map(document -> {
      var tag = new Tag();
      tag.setTagName(document.getString("tagName"));
      tag.setMovieTitle(document.getString("movieTitle"));
      tag.setMovieId(document.getString("movieId"));
      tag.setUsername(document.getString("username"));
      tag.setPrivacy(document.getString("privacy"));
      tag.setDateTimeCreated(document.get("dateTimeCreated").toString());
      tag.setState(document.getString("state"));
      return tag;
    });
    var list = new ArrayList<Tag>();
    ratings.forEach(list::add);
    return list;
  }

  public List<Tag> getTagsWithMovieId(String movieId) {
    var tags = getTagCollection();
    var filter = Filters.eq("movieId", movieId);
    return getTagsWithFilter(tags, filter);
  }

  public List<Tag> getTagsWithTagName(String tagName) {
    MongoCollection<Document> tags = getTagCollection();
    Bson filter = Filters.eq("tagName", tagName);
    return getTagsWithFilter(tags, filter);
  }

  public List<Tag> getTagsWithUsername(String username) {
    MongoCollection<Document> tags = getTagCollection();
    Bson filter = Filters.eq("username", username.toLowerCase());
    return getTagsWithFilter(tags, filter);
  }

  /**
   * Returns a string that reports the status of the tag.
   * @param requesterUsername username of tag creator
   * @param movieId Mongo hexId of the movie that tag is attached to
   * @param tagName name of the tag to find
   * @return String of either "upvote", "downvote", or "noTag".
   */
  public String getTagStatus(String requesterUsername, String movieId, String tagName){
    // get the collections
    MongoCollection<Document> tagCollection = getTagCollection();

    // attempt to get the tag
    Bson tagFilter = Filters.and(
            Filters.eq("username", requesterUsername.toLowerCase()),
            Filters.eq("tagName", tagName),
            Filters.eq("movieId", movieId));

    List<Tag> tags = getTagsWithFilter(tagCollection, tagFilter);

    // if the tag does not exist
    if(tags.isEmpty()){
      // return "noTag"
      return "noTag";
    }
    // else find the state
    else {
      // return the state
      return tags.get(0).getState();
    }
  }
  /*
   * Tag Update Functions
   *
   * upvoteTag
   * downvoteTag
   */
  public void upvoteTag(String requesterUsername, String tagName, String movieId){
    // get the collections
    MongoCollection<Document> tagCollection = getTagCollection();

    // try to get your tag for this movie
    Bson tagFilter = Filters.and(
            Filters.eq("username", requesterUsername.toLowerCase()),
            Filters.eq("tagName", tagName),
            Filters.eq("movieId", movieId));

    List<Tag> tags = getTagsWithFilter(tagCollection, tagFilter);
    // if you have not already made this tag for this movie
    if(tags.isEmpty()) {
      // make this tag in your name for this movie
      createTag(tagName, movieId, requesterUsername, "public");
    } else if (tags.get(0).getState().equals("downvote")){
      // change to upvote
      Bson upvoteUpdate = Updates.set("state", "upvote");
      tagCollection.updateOne(tagFilter, upvoteUpdate);
    }
    // otherwise you already have made this tag. Why are you doing this please don't do this return nothing
  }

  public void downvoteTag(String requesterUsername, String tagName, String movieId){
    // get the collections
    MongoCollection<Document> tagCollection = getTagCollection();

    Bson tagFilter = Filters.and(
            Filters.eq("username", requesterUsername.toLowerCase()),
            Filters.eq("tagName", tagName),
            Filters.eq("movieId", movieId));
    // try to get your tag for this movie
    List<Tag> tags = getTagsWithFilter(tagCollection, tagFilter);

    // if the tag exists and is currently set to upvote
    if(!tags.isEmpty() && tags.get(0).getState().equals("upvote")){
      // set the state to downvote
      Bson downvoteUpdate = Updates.set("state", "downvote");
      tagCollection.updateOne(tagFilter, downvoteUpdate);
    }
    // otherwise the tag does not exist
    else {
      // therefore create a tag for in the users name
      createTag(tagName, movieId, requesterUsername, "public");

      // set the state to downvote
      Bson downVoteUpdate = Updates.set("state", "downvote");
      tagCollection.updateOne(tagFilter, downVoteUpdate);
    }
  }

  /*
   * Tag Delete Functions
   *
   * deleteTagNameWithTagName
   */

  /**
   * Removes a tag from a database. Tags can be deleted by the creator of the tag and by anyone who is an admin.
   * (For now everyone is an admin since this has not been fully implemented).
   * @param tagName name of the tag to remove
   * @param movieId MongoDB hexId of the movie to remove the tag from
   * @param requesterUsername username of the person trying to delete the tag
   * @return TRUE if the tag is deleted FALSE otherwise
   */
  public boolean deleteTagWithTagName(String tagName, String movieId, String tagOwnerUsername, String requesterUsername){
    // get collections
    MongoCollection<Document> tagCollection = getTagCollection();

    // attempt to get the tag from the tag collection
    Bson movieFilter = Filters.eq("movieId", movieId);
    Bson tagNameFilter = Filters.eq("tagName", tagName);
    Bson ownerFilter = Filters.eq("username", tagOwnerUsername.toLowerCase());
    Bson deleteFilter = Filters.and(movieFilter, tagNameFilter, ownerFilter);

    Tag tag = getTagsWithFilter(tagCollection, deleteFilter).get(0);
    // if the tag exists, and you are the owner or you are an admin
    if(tag!=null && (tagOwnerUsername.toLowerCase().equals(requesterUsername.toLowerCase())  || checkAdmin(requesterUsername))){
        // remove the tag from the tags collection
        tagCollection.deleteOne(deleteFilter);
        return true;
    }
    // otherwise return false
    return false;
  }


  /*
   * Other helper functions. These are used to support the base CRUD functions for each database object
   *
   * getMovieDocumentWithHexId
   */
  /**
   * retrieves movie using MongoDB unique hex identifier. Creates a ObjectID object to return the movie Document.
   * @param hexID String representation of the hex id.
   * @return Document of the movie matching the id, null if id is not found
   */
    public Document getMovieDocumentWithHexId(String hexID){
      MongoCollection<Document> movieCollection = getMovieCollection();
      ObjectId movieId = new ObjectId(hexID);
      return movieCollection.find(Filters.eq("_id", movieId)).first();
    }

  /**
   * One day will be used to check if a user is an admit. For now everyone is an admin
   * @param username username to check if they are an admin.
   * @return TRUE always
   */
  public boolean checkAdmin(String username){
      // TODO if this is ever implemented remember things need to work off of lowercase names
      return true;
    }
}