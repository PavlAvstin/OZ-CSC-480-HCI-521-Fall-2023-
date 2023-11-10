package edu.oswego.cs.rest;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseController {
  String mongoDatabaseName = System.getenv("MONGO_MOVIE_DATABASE_NAME");
  String mongoURL = System.getenv("MONGO_MOVIE_URL");

  public MongoDatabase getMovieDatabase() {
    MongoClient mongoClient = MongoClients.create(mongoURL);
    return mongoClient.getDatabase(mongoDatabaseName);
  }
}
