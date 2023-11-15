package edu.oswego.cs.rest;

import edu.oswego.cs.rest.JsonClasses.Review;
import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
  private static String mongoDatabaseName = System.getenv("MONGO_MOVIE_DATABASE_NAME");
  private static String mongoURL = System.getenv("MONGO_MOVIE_URL");
  private static MongoClient mongoClient = MongoClients.create(mongoURL);

  public MongoDatabase getMovieDatabase() {
    return mongoClient.getDatabase(mongoDatabaseName);
  }

  /*
   * Database get collection methods
   */
  public MongoCollection<Document> getMovieCollection() {
    return getMovieDatabase().getCollection("movies");
  }

  public MongoCollection<Document> getReviewCollection() {
    return getMovieDatabase().getCollection("reviews");
  }

  /*
   * Review Create functions
   *
   * createReview
   */
  /**
   * Creates and stores a review in the database. Reviews are the freeform text user generated data. Users are not
   * allowed to add a review for a movie that does not exist. Users are currently allowed to make multiple reviews
   * for the same movie.
   *
   * @param reviewDescription Freeform text from the user. No limits in size.
   * @param username the user who created the review
   */
  public void createReview(String movieIdString, String reviewDescription, String username, String privacy){
    // get collections
    MongoCollection<Document> reviewCollection = getReviewCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();

    ObjectId movieId = new ObjectId(movieIdString);
    // get the movie object to make sure it exists
    Document movie = movieCollection.find(Filters.eq("_id", movieId)).first();

    // if the movie exists
    if(null != movie) {
      // get the current date time to attach to the new review
      BsonDateTime dateTimeCreated = new BsonDateTime(System.currentTimeMillis());
      // create a new review
      Document newReview = new Document("movieId", movieIdString).append("reviewDescription", reviewDescription)
              .append("username", username.toLowerCase()).append("dateTimeCreated", dateTimeCreated)
              .append("privacy", privacy);
      reviewCollection.insertOne(newReview);
    }
  }

  /*
   * Review Get functions
   *
   * getReviewsWithFilter
   *
   * getReviewsWithMovieId
   * getReviewsWithUser
   */
  private static ArrayList<Review> getReviewsWithFilter(MongoCollection<Document> reviewsCollection, Bson filter) {
    var reviews = reviewsCollection.find(filter).map(document -> {
      var re = new Review();
      re.setUsername(document.getString("username"));
      re.setReviewDescription(document.getString("reviewDescription"));
      re.setMovieId(document.getString("movieId"));
      re.setDateTimeCreated(document.get("dateTimeCreated").toString());
      re.setPrivacy(document.getString("privacy"));
      return re;
    });
    var list = new ArrayList<Review>();
    reviews.forEach(list::add);
    return list;
  }

  public List<Review> getReviewsWithMovieId(String movieId) {
    var reviews = getReviewCollection();
    var filter = Filters.eq("movieId", movieId);
    return getReviewsWithFilter(reviews, filter);
  }

  public List<Review> getReviewsWithUsername(String username) {
    var reviews = getReviewCollection();
    var filter = Filters.eq("username", username);
    return getReviewsWithFilter(reviews, filter);
  }

  /*
   * Review Update functions
   */

  /*
   * Review Delete functions
   */

  /*
   * Other helper functions
   */
}
