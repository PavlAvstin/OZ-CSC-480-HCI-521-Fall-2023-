package edu.oswego.cs.rest;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class DatabaseController {
  String mongoDatabaseName = System.getenv("MONGO_MOVIE_DATABASE_NAME");
  String mongoURL = System.getenv("MONGO_MOVIE_URL");

  public MongoDatabase getMovieDatabase() {
    MongoClient mongoClient = MongoClients.create(mongoURL);
    return mongoClient.getDatabase(mongoDatabaseName);
  }

  public MongoCollection<Document> getFlagCollection() {
    return getMovieDatabase().getCollection("flags");
  }

  public MongoCollection<Document> getMovieCollection() {
    return getMovieDatabase().getCollection("movies");
  }

  public MongoCollection<Document> getActorCollection() {
    return getMovieDatabase().getCollection("actors");
  }

  public MongoCollection<Document> getRatingCollection() {
    return getMovieDatabase().getCollection("ratings");
  }

  public MongoCollection<Document> getUserAssociatedRatingCollection() {
    return getMovieDatabase().getCollection("userAssociatedRatings");
  }

  public MongoCollection<Document> getReviewCollection() {
    return getMovieDatabase().getCollection("reviews");
  }

  /**
   * Create CRUD operations
   *
   */


  /**
   *
   *
   * Users are not allowed to create a flag for a movie that does not already exist. If the movie does not exist
   *
   * @param flagName
   * @param movieTitleToAdd
   * @param movieId
   */
  public void createFlag(String flagName, String movieTitleToAdd, String movieId) {
    // get the collections
    MongoCollection<Document> flagCollection = getFlagCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();

    // grab the two possible iterations of the flag that could exist
    Document existsAndFlagged = flagCollection.find(Filters.eq("movieTitles", movieTitleToAdd)).first();
    Document existingFlag = flagCollection.find(Filters.eq("flagName", flagName)).first();

    // if the flag exists and the movie is already flagged
    if (null != existsAndFlagged){ }

    // if the flag exists and the movie is not flagged
    else if (null != existingFlag) {
      Document movie = movieCollection.find(Filters.eq("id", movieId)).first();
      // if the movie exists
      if(null != movie) {
        // push the movieName to the flag list
        Bson flagUpdateOperation = Updates.push("movieTitles", movieTitleToAdd);
        flagCollection.updateOne(existingFlag, flagUpdateOperation);
        // push the flagName to the movie list
        Bson movieUpdateOperation = Updates.push("flagNames", flagName);
        movieCollection.updateOne(movie, movieUpdateOperation);
      }
      // if the movie does not exist
      else{ }
    }
    // if the flag does not exist
    else {
      Document movie = movieCollection.find(Filters.eq("id", movieId)).first();
      // if the movie exists
      if(null != movie) {
        // create the flag and add to the collection
        Document newFlag = new Document("flagName", flagName).append("movieTitles", movieTitleToAdd);
        flagCollection.insertOne(newFlag);
        // push the flagName to the movie list
        Bson movieUpdateOperation = Updates.push("flagNames", flagName);
        movieCollection.updateOne(movie, movieUpdateOperation);
      }
      // if the movie does not exist
      else{ }
    }
  }

  public void createRating(String ratingCategoryName){

  }

  /**
   *
   * @param movieTitle
   * @param movieId
   * @param reviewTitle
   * @param reviewDescription
   * @param userName
   */
  public void createReview(String movieTitle, String movieId, String reviewTitle, String reviewDescription, String userName){
    // get collections
    MongoCollection<Document> reviewCollection = getReviewCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();

    // get the movie object to make sure it exists
    Document movie = movieCollection.find(Filters.eq("id", movieId)).first();

    // if the movie exists
    if(null != movie) {
      // create a new review
      Document newReview = new Document("movieTitle", movieTitle).append("reviewTitle", reviewTitle)
              .append("reviewDescription", reviewDescription).append("userName", userName);
      reviewCollection.insertOne(newReview);
    }
    // if the movie does not exist
    else{ }
  }

  /**
   *
   * @param actorName
   * @param actorId
   * @param dob
   * @param movieTitle
   * @param movieId
   */
  public void createActor(String actorName, String actorId, String dob, String movieTitle, String movieId){
    // get collections
    MongoCollection<Document> actorCollection = getActorCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();

    // get the actor object to see if it exists
    Document actor = actorCollection.find(Filters.eq("id", actorId)).first();

    // if the actor exists
    if(null != actor) { }

    // if the actor does not exist
    else{
      // get the movie object to make sure it exists
      Document movie = movieCollection.find(Filters.eq("id", movieId)).first();
      // if the movie exists
      if(null != movie) {
        // create a new actor
        Document newReview = new Document("id", actorId).append("name", actorName)
                .append("dob", dob).append("movies", movieTitle);
        actorCollection.insertOne(newReview);

        // add actor to movie cast
        Bson movieUpdateOperation = Updates.push("principalCast", actorName);
        movieCollection.updateOne(movie, movieUpdateOperation);
      }
      // if the movie does not exist
      else{ }
    }
  }

  public void createMovie(String movieTitle, String movieId, String director, String principalCast, String releaseDate,
                          String runtime, String writers, String plotSummary){
    // get collections
    MongoCollection<Document> movieCollection = getMovieCollection();

    // get the movie object to see if it exists
    Document movie = movieCollection.find(Filters.eq("id", movieId)).first();

    // if the movie exists
    if (null != movie) { }

    // if the movie does not exist
    else{
      // create a new movie and add it to the movie collection
      Document newMovie = new Document("id", movieId).append("Title", movieTitle).append("Director", director)
              .append("releaseDate", releaseDate).append("Runtime", runtime).append("plotSummary", plotSummary);
      movieCollection.insertOne(newMovie);
    }
  }

  /**
   * Helper functions
   */

}
