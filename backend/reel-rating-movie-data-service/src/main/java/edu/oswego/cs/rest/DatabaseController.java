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

}
