package edu.oswego.cs.rest;

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

  public MongoCollection<Document> getActorCollection() {
    return getMovieDatabase().getCollection("actors");
  }

    /**
   * Creates and adds an actor to the database. Users cannot create an actor if the actor already exists by name.
   * This may need to be reconfigured to allow for two actors with the same name.
   *
   * @param actorName actor's name
   * @param dob the date of birth of the actor
   * @param movieId a movie that the actor appears in. More can be added later
   */
  public void createActor(String actorName, String dob, String movieId){
    // get collections
    MongoCollection<Document> actorCollection = getActorCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();

    ObjectId movieIdObject = new ObjectId(movieId);
    // get the movie object to make sure it exists
    Document movie = movieCollection.find(Filters.eq("_id", movieIdObject)).first();
    // if the movie exists
    if(null != movie) {
      // create a new actor
      Document newActor = new Document().append("name", actorName)
                .append("dob", dob);
      actorCollection.insertOne(newActor);
      Bson actorMovieUpdateOperation = Updates.push("movies", movieId);
      actorCollection.updateOne(newActor, actorMovieUpdateOperation);

      String actorId = newActor.getObjectId("_id").toHexString();
      // add actor to movie cast
      Bson movieUpdateOperation = Updates.push("principalCast", actorId);
      movieCollection.updateOne(movie, movieUpdateOperation);
    }
    // if the movie does not exist
    else{ }
  }
}
