package edu.oswego.cs.rest;

import edu.oswego.cs.rest.JsonClasses.Actor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
  String mongoDatabaseName = System.getenv("MONGO_MOVIE_DATABASE_NAME");
  String mongoURL = System.getenv("MONGO_MOVIE_URL");

  public MongoDatabase getMovieDatabase() {
    MongoClient mongoClient = MongoClients.create(mongoURL);
    return mongoClient.getDatabase(mongoDatabaseName);
  }

  /*
   * Database get collection methods
   */
  public MongoCollection<Document> getMovieCollection() {
    return getMovieDatabase().getCollection("movies");
  }

  public MongoCollection<Document> getActorCollection() {
    return getMovieDatabase().getCollection("actors");
  }

  /*
   * Actor Create functions
   *
   * createActor
   */

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

  /*
   * Actor get functions
   *
   * getActorsWithFilter
   *
   * getActorByName
   * getActorWithMovieId
   * getActorWithActorId
   *
   */

  /**
   * Creates and returns a list of actors based on the given filter.
   * @param actorsCollection MongoDB Collection of Actor documents
   * @param filter the filter to apply to the document collection
   * @return ArrayList of actors that fit the filter
   */
  private static ArrayList<Actor> getActorsWithFilter(MongoCollection<Document> actorsCollection, Bson filter) {
    var actors = actorsCollection.find(filter).map(document -> {
      var a = new Actor();
      a.setName(document.getString("name"));
      a.setDateOfBirth(document.getString("dob"));
      a.setId(document.getObjectId("_id").toHexString());
      a.setMovies(document.getList("movies", String.class));
      return a;
    });
    var list = new ArrayList<Actor>();
    actors.forEach(list::add);
    return list;
  }

  /**
   * Returns all actors of the given name.
   * @param name Name of the actor to find
   * @return ArrayList of Actors whose name matches the <code>name</code> parameter
   */
  public List<Actor> getActorWithName(String name) {
    var actorsCollection = getActorCollection();
    var filter = Filters.eq("name", name);
    return getActorsWithFilter(actorsCollection, filter);
  }

  /**
   * Returns all actors listed in primary cast of a provided movie.
   * @param movieId MongoDB hexId of movie to search through
   * @return ArrayList of Actors from the movie
   */
  public List<Actor> getActorWithMovieId(String movieId) {
    //getting the movie by ID
    var movie = getMovieDocumentWithHexId(movieId);
    if (movie != null) {
      //Looking at actors in movie
      var actorIds = movie.getList("principalCast", String.class);
      var actors = new ArrayList<Actor>();
      if (actorIds == null) {
        return new ArrayList<>();
      }
      //Putting actors into a list
      for (var actorId : actorIds) {
        var actorQuery = getActorWithActorId(actorId);
        if (actorQuery != null) {
          actors.add(actorQuery);
        }
      }
      //return actor list
      return actors;
    }
    //return null if movie doesn't exist by ID.
    return null;
  }

  /**
   * Returns actor matching the given Id or null if the there is no actor by the hexId.
   * @param actorId MongoDB hexId of the actor to find
   * @return Actor object of the actor that has the given hexId.
   */
  public Actor getActorWithActorId(String actorId) {
    var actorsCollection = getActorCollection();
    ObjectId actorObjectId = new ObjectId(actorId);
    var filter = Filters.eq("_id", actorObjectId);
    var actorDocument = actorsCollection.find(filter).first();
    if (actorDocument != null) {
      Actor actor = new Actor();
      actor.setName(actorDocument.getString("name"));
      actor.setId(actorId);
      actor.setDateOfBirth(actorDocument.getString("dob"));
      actor.setMovies(actorDocument.getList("movies", String.class));
      return actor;
    }
    return null;
  }
  /*
   * Actor Update functions
   */

  /*
   * Actor Delete functions
   */

  /*
   * Other helper functions
   *
   * getMovieDocumentWithHexId
   */
  public Document getMovieDocumentWithHexId(String hexID){
    MongoCollection<Document> movieCollection = getMovieCollection();
    ObjectId movieId = new ObjectId(hexID);
    return movieCollection.find(Filters.eq("_id", movieId)).first();
  }
}
