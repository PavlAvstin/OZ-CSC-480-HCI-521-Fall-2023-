package edu.oswego.cs.rest;

import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

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

  public MongoCollection<Document> getReviewCollection() {
    return getMovieDatabase().getCollection("reviews");
  }

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
              .append("username", username).append("dateTimeCreated", dateTimeCreated)
              .append("privacy", privacy);
      reviewCollection.insertOne(newReview);
    }
  }
  
}
