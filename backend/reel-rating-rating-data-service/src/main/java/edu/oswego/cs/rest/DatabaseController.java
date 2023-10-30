package edu.oswego.cs.rest;

import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

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

  public MongoCollection<Document> getMovieCollection() {
    return getMovieDatabase().getCollection("movies");
  }

  public MongoCollection<Document> getRatingCollection() {
    return getMovieDatabase().getCollection("ratings");
  }

    /**
   * Creates and adds a rating object associated with a movie to the database. Employs a series
   * of checks to make sure the movie exists and the ratingCategory does not already exist.
   * @param ratingName Name of the rating category. For example, "How Harrison Ford is it", "Stickiness"
   * @param movieIdHexString movie unique MongoDB identifier
   * @param username user to associate with the rating
   * @param userRating value assigned by the user
   * @param upperbound upperbound of the rating scale. 0 < upperbound < 11
   */
  public void createRating(String ratingName, String userRating, String upperbound, String username,
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
                  .append("privacy", privacy);
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
}
