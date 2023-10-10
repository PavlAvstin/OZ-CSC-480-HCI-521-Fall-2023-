package edu.oswego.cs.rest;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import edu.oswego.cs.rest.JsonClasses.Actor;
import edu.oswego.cs.rest.JsonClasses.Movie;
import edu.oswego.cs.rest.JsonClasses.Rating;
import edu.oswego.cs.rest.JsonClasses.Review;

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
   * Updates the movie title in the movies collection, flag collection, actor collection,
   * ratings collection, userassociatedratings collections, and reviews collection
   * @param id
   * @param movieTitle
   */
  public void updateMovieTitle(String id, String movieTitle) {
    MongoCollection<Document> movies = getMovieCollection();
    Bson idFilter = Filters.eq("id", id);
    String oldMovieTitle = movies.find(idFilter).first().getString("title");
    Bson updateTitle = Updates.set("title", movieTitle);
    movies.updateOne(idFilter, updateTitle);
    
    MongoCollection<Document> flags = getFlagCollection();
    Bson movieTitleFilter = Filters.eq("movieTitle", oldMovieTitle);
    Bson updateMovieTitle = Updates.set("movieTitle", movieTitle);
    flags.updateMany(movieTitleFilter, updateMovieTitle);

    MongoCollection<Document> actors = getActorCollection();
    Bson movieTitleFilterForActor = Filters.eq("movies", oldMovieTitle);
    Bson updateMovieTitleForActor = Updates.set("movies", movieTitle);
    actors.updateMany(movieTitleFilterForActor, updateMovieTitleForActor);

    MongoCollection<Document> ratings = getRatingCollection();
    Bson movieTitleFilterForRatings = Filters.eq("movieTitle", oldMovieTitle);
    Bson updateMovieTitleForRatings = Updates.set("movieTitle", movieTitle);
    ratings.updateMany(movieTitleFilterForRatings, updateMovieTitleForRatings);

    MongoCollection<Document> userAssocRatings = getUserAssociatedRatingCollection();
    Bson movieTitleFilterForUserAssocRatings = Filters.eq("movieTitle", oldMovieTitle);
    Bson updateMovieTitleForUserAssocRatings = Updates.set("movieTitle", movieTitle);
    userAssocRatings.updateMany(movieTitleFilterForUserAssocRatings, updateMovieTitleForUserAssocRatings);

    MongoCollection<Document> reviews = getReviewCollection();
    Bson movieTitleFilterForReviews = Filters.eq("movieTitle", oldMovieTitle);
    Bson updateMovieTitleForReviews = Updates.set("movieTitle", movieTitle);
    reviews.updateMany(movieTitleFilterForReviews, updateMovieTitleForReviews);
  }

  public void updateDirector(String id, String director) {
    MongoCollection<Document> movies = getMovieCollection();
    Bson idFilter = Filters.eq("id", id);
    Bson updateDirector = Updates.set("director", director);
    movies.updateOne(idFilter, updateDirector);
  }

  public void updateReleaseDate(String id, String releaseDate) {
    MongoCollection<Document> movies = getMovieCollection();
    Bson idFilter = Filters.eq("id", id);
    Bson updateReleaseDate = Updates.set("releaseDate", releaseDate);
    movies.updateOne(idFilter, updateReleaseDate);
  }

  public void updateRunTime(String id, String runTime) {
    MongoCollection<Document> movies = getMovieCollection();
    Bson idFilter = Filters.eq("id", id);
    Bson updateRunTime = Updates.set("runTime", runTime);
    movies.updateOne(idFilter, updateRunTime);
  }

  public void updatePlotSummary(String id, String plotSummary) {
    MongoCollection<Document> movies = getMovieCollection();
    Bson idFilter = Filters.eq("id", id);
    Bson updatePlotSummary = Updates.set("plotSummary", plotSummary);
    movies.updateOne(idFilter, updatePlotSummary);
  }

  public void updateActor(String id, String name, String dob, List<String> movies) {
    MongoCollection<Document> actors = getActorCollection();
    Bson idFilter = Filters.eq("id", id);
    Bson updateName = Updates.set("name", name);
    actors.updateOne(idFilter, updateName);
    Bson updateDOB = Updates.set("dob", dob);
    actors.updateOne(idFilter, updateDOB);
    Bson removeMovies = Updates.unset("movies");
    actors.updateOne(idFilter, removeMovies);
    Bson addMovies = Updates.pushEach("movies", movies);
    actors.updateOne(idFilter, addMovies);
  }

  public void updateActorName(String id, String name) {
    MongoCollection<Document> actors = getActorCollection();
    Bson idFilter = Filters.eq("id", id);
    Bson updateName = Updates.set("name", name);
    actors.updateOne(idFilter, updateName);
  }

  public void updateActorDob(String id, String dob) {
    MongoCollection<Document> actors = getActorCollection();
    Bson idFilter = Filters.eq("id", id);
    Bson updateDOB = Updates.set("dob", dob);
    actors.updateOne(idFilter, updateDOB);
  }

  // Allows bulk change of movies
  public void updateActorMovies(String id, List<String> movies) {
    MongoCollection<Document> actors = getActorCollection();
    Bson idFilter = Filters.eq("id", id);
    Bson removeMovies = Updates.unset("movies");
    actors.updateOne(idFilter, removeMovies);
    Bson addMovies = Updates.pushEach("movies", movies);
    actors.updateOne(idFilter, addMovies);
  }

  public void updateRatingCategoryName(String ratingCategoryId, String ratingName) {
    MongoCollection<Document> ratings = getRatingCollection();
    Bson idFilter = Filters.eq("ratingCategoryId", ratingCategoryId);
    Bson updateRatingName = Updates.set("ratingName", ratingName);
    String oldRatingName = ratings.find(idFilter).first().getString("ratingName");
    ratings.updateOne(idFilter, updateRatingName);

    MongoCollection<Document> userAssocRatings = getUserAssociatedRatingCollection();
    Bson ratingNameFilter = Filters.eq("ratingName", oldRatingName);
    userAssocRatings.updateOne(ratingNameFilter, updateRatingName);
  }

  // Could just call deleteUserRating then createUserRating
  public void updateUserRating(String ratingCategoryId, String ratingName, String movieTitle, String userName, String userRating) {
    MongoCollection<Document> ratings = getRatingCollection();
    Bson idFilter = Filters.eq("ratingCategoryId", ratingCategoryId);
    Bson userNameFilter = Filters.eq("userName", userName);
    Bson idAndUserName = Filters.and(idFilter, userNameFilter);
    Document oldRating = ratings.find(idAndUserName).first();
    String oldRatingName = oldRating.getString("ratingName");
    String oldMovieTitle = oldRating.getString("movieTitle");
    String oldUserRating = oldRating.getString("userRating");

    Bson movieTitleAndUserRatingAndUserName = Filters.and(userNameFilter, Filters.eq("movieTitle", oldMovieTitle), Filters.eq("userRating", oldUserRating));
    ratings.deleteOne(movieTitleAndUserRatingAndUserName);
  }

  public void updateCategoryRatingName(String ratingCategoryId, String ratingName) {
    MongoCollection<Document> ratings = getRatingCollection();
    Bson idFilter = Filters.eq("ratingCategoryId", ratingCategoryId);
    String oldRatingName = ratings.find(idFilter).first().getString("ratingName");
    Bson updateRatingName = Updates.set("ratingName", ratingName);
    ratings.updateOne(idFilter, updateRatingName);

    MongoCollection<Document> userAssocRatings = getUserAssociatedRatingCollection();
    Bson oldNameFilter = Filters.eq("ratingName", oldRatingName);
    userAssocRatings.updateMany(oldNameFilter, updateRatingName);
  }

  public void updateUserRating(String username, String ratingName, String movieTitle, String userRating) {
    MongoCollection<Document> ratings = getRatingCollection();
    Bson userNameFilter = Filters.eq("userName", username);
    Bson ratingNameFilter = Filters.eq("ratingName", ratingName);
    Bson movieTitleFilter = Filters.eq("movieTitle", movieTitle);
    Bson userNameAndRatingNameAndMovieTitleFilter = Filters.and(userNameFilter, ratingNameFilter, movieTitleFilter);
    Bson updateUserRating = Updates.set("userRating", userRating);
    ratings.updateOne(userNameAndRatingNameAndMovieTitleFilter, updateUserRating);

    MongoCollection<Document> userAssocRatings = getUserAssociatedRatingCollection();
    userAssocRatings.updateOne(userNameAndRatingNameAndMovieTitleFilter, updateUserRating);
  }

  public void updateReviewTitle(String movieTitle, String username, String reviewTitle) {
    MongoCollection<Document> reviews = getReviewCollection();
    Bson userNameFilter = Filters.eq("userName", username);
    Bson movieTitleFilter = Filters.eq("movieTitle", movieTitle);
    Bson userNameAndMovieTitleFilter = Filters.and(userNameFilter, movieTitleFilter);
    Bson updateReviewTitle = Updates.set("reviewTitle", reviewTitle);
    reviews.updateOne(userNameAndMovieTitleFilter, updateReviewTitle);
  }

  public void updateReviewDescription(String movieTitle, String username, String reviewDescription) {
    MongoCollection<Document> reviews = getReviewCollection();
    Bson userNameFilter = Filters.eq("userName", username);
    Bson movieTitleFilter = Filters.eq("movieTitle", movieTitle);
    Bson userNameAndMovieTitleFilter = Filters.and(userNameFilter, movieTitleFilter);
    Bson updateReviewDesc = Updates.set("reviewDescription", reviewDescription);
    reviews.updateOne(userNameAndMovieTitleFilter, updateReviewDesc);
  }

  public void updateReview(String movieTitle, String username, String reviewTitle, String reviewDescription) {
    updateReviewTitle(movieTitle, username, reviewTitle);
    updateReviewDescription(movieTitle, username, reviewDescription);
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
   */
  public void createFlag(String flagName, String movieIdHexString) {
    // get the collections
    MongoCollection<Document> flagCollection = getFlagCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();
    ObjectId movieId = new ObjectId(movieIdHexString);
    Document movie = movieCollection.find(Filters.eq("_id", movieId)).first();
    if (movie == null) {return;}
    // grab the two possible iterations of the flag that could exist
    Document existsAndFlagged = flagCollection.find(Filters.eq("movieTitles", movie.getString("title"))).first();
    Document existingFlag = flagCollection.find(Filters.eq("flagName", flagName)).first();

    // if the flag exists and the movie is already flagged
    if (null != existsAndFlagged){ }

    // if the flag exists and the movie is not flagged
    else if (null != existingFlag) {
      // if the movie exists
      if(null != movie) {
        // push the movieName to the flag list
        Bson flagUpdateOperation = Updates.push("movieTitles", movie.getString("title"));
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
      // if the movie exists
      if(null != movie) {
        // create the flag and add to the collection
        Document newFlag = new Document("flagName", flagName).append("movieTitles", movie.getString("title"));
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
   * @param reviewTitle
   * @param reviewDescription
   * @param userName
   */
  public void createReview(String movieIdString, String reviewTitle, String reviewDescription, String userName){
    // get collections
    MongoCollection<Document> reviewCollection = getReviewCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();

    ObjectId movieId = new ObjectId(movieIdString);
    // get the movie object to make sure it exists
    Document movie = movieCollection.find(Filters.eq("_id", movieId)).first();

    // if the movie exists
    if(null != movie) {
      // create a new review
      Document newReview = new Document("movieId", movieIdString).append("reviewTitle", reviewTitle)
              .append("reviewDescription", reviewDescription).append("userName", userName);
      reviewCollection.insertOne(newReview);
    }
    // if the movie does not exist
    else{ }
  }

  /**
   *
   * @param actorName
   * @param dob
   * @param movieTitle
   */
  public void createActor(String actorName, String dob, String movieTitle){
    // get collections
    MongoCollection<Document> actorCollection = getActorCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();

    // TODO verify the actor does not already exist
    String actorId = "";
    // get the actor object to see if it exists
    Document actor = actorCollection.find(Filters.eq("id", actorId)).first();

    // if the actor exists
    if(null != actor) { }

    // if the actor does not exist
    else{
      // get the movie object to make sure it exists
      Document movie = movieCollection.find(Filters.eq("title", movieTitle)).first();
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

  public void createMovie(String movieTitle, String director, String releaseDate,
                          String runtime, String writers, String plotSummary){
    // get collections
    MongoCollection<Document> movieCollection = getMovieCollection();
      // create a new movie and add it to the movie collection
    Document newMovie = new Document().append("title", movieTitle).append("director", director)
            .append("releaseDate", releaseDate).append("runtime", runtime).append("plotSummary", plotSummary);
    movieCollection.insertOne(newMovie);
  }

    private static ArrayList<Movie> getMoviesWithFilter(MongoCollection<Document> moviesCollection, Bson filter) {
    var movies = moviesCollection.find(filter).map(document -> {
      var m = new Movie();
      m.setDirector(document.getString("director"));
      m.setRuntime(document.getString("runtime"));
      m.setSummary(document.getString("summary"));
      m.setTitle(document.getString("title"));
      m.setWriters(document.getString("writers"));
      m.setReleaseDate(document.getString("releaseDate"));
      m.setId(document.getObjectId("_id").toHexString());
      return m;
    });
    var list = new ArrayList<Movie>();
    movies.forEach(list::add);
    return list;
  }

  private static ArrayList<Actor> getActorsWithFilter(MongoCollection<Document> actorsCollection, Bson filter) {
    var actors = actorsCollection.find(filter).map(document -> {
      var a = new Actor();
      a.setName(document.getString("name"));
      a.setDateOfBirth(document.getString("dateOfBirth"));

      return a;
    });
    var list = new ArrayList<Actor>();
    actors.forEach(list::add);
    return list;
  }

  private static ArrayList<Review> getReviewsWithFilter(MongoCollection<Document> reviewsCollection, Bson filter) {
    var reviews = reviewsCollection.find(filter).map(document -> {
      var re = new Review();
      re.setReviewTitle(document.getString("reviewTitle"));
      re.setReviewDescription(document.getString("reviewDescription"));
      re.setMovieId(document.getString("movieId"));

      return re;
    });
    var list = new ArrayList<Review>();
    reviews.forEach(list::add);
    return list;
  }

  private static ArrayList<Rating> getRatingsWithFilter(MongoCollection<Document> ratingsCollection, Bson filter) {
    var ratings = ratingsCollection.find(filter).map(document -> {
      var ra = new Rating();
      ra.setRatingName(document.getString("ratingName"));
      ra.setUserRating(document.getString("userRating"));
      ra.setMovieTitle(document.getString("movieTitle"));


      return ra;
    });
    var list = new ArrayList<Rating>();
    ratings.forEach(list::add);
    return list;
  }



  public List<Movie> getMoviesWithFlag(String flag) {
    var moviesCollection = getMovieCollection();
    var filter = Filters.eq("flagNames", flag);
    return getMoviesWithFilter(moviesCollection, filter);
  }

  public List<Movie> getMoviesWithRatingCategory(String ratingCategory) {
    var moviesCollection = getMovieCollection();
    var filter = Filters.eq("ratingCategoryNames", ratingCategory);
    return getMoviesWithFilter(moviesCollection, filter);
  }

  public List<Movie> getMoviesWithActor(String actor) {
    var moviesCollection = getMovieCollection();
    var filter = Filters.eq("actorNames", actor);
    return getMoviesWithFilter(moviesCollection, filter);
  }

  public List<Movie> getMoviesWithTitle(String title) {
    var moviesCollection = getMovieCollection();
    var filter = Filters.eq("title", title);
    return getMoviesWithFilter(moviesCollection, filter);
  }

  public List<Actor> getActorByName(String title) {
    var actorsCollection = getActorCollection();
    var filter = Filters.eq("title", title);
    return getActorsWithFilter(actorsCollection, filter);
  }

  public List<Rating> getUserAssociatedRatings(String userName) {
    var ratings = getRatingCollection();
    var filter = Filters.eq("user", userName);
    return getRatingsWithFilter(ratings, filter);
  }

  public List<Rating> getRatingsInRatingsCategory(String category) {
    var ratings = getRatingCollection();
    var filter = Filters.eq("category", category);
    return getRatingsWithFilter(ratings, filter);
  }

  public List<Review> getReviewsByMovieId(String movieId) {
    var reviews = getReviewCollection();
    var filter = Filters.eq("movieId", movieId);
    return getReviewsWithFilter(reviews, filter);
  }

  public List<Review> getReviewsByUser(String userName) {
    var reviews = getReviewCollection();
    var filter = Filters.eq("userName", userName);
    return getReviewsWithFilter(reviews, filter);
  }

  /**
   * 
   * @param flagName
   * @param movieId
   * @param movieTitle
   */
  //remove a flag from a specific movie 
  public void deleteFlag(String flagName, String movieId, String movieTitle){
    //get flags and movie collections
    MongoCollection<Document> flagCollection = getFlagCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();

    //filters movie with the input movie ID
    Bson movieQuery = Filters.eq("id", movieId);
    Document movieWithId = movieCollection.find(movieQuery).first();
    if(movieWithId != null){
    //remove flag from movie with the corresspond ID
    Bson flagRemoveOp = Updates.pull("flagNames", flagName);
    movieCollection.updateOne(movieWithId, flagRemoveOp);

    //find the flag needed to be deleted
    Bson titleQuery = Filters.eq("flagName", flagName);
    Document existingFlag = flagCollection.find(titleQuery).first();
    //remove movie title from the flag
    Bson flagRemoveOP2 = Updates.pull("movieTitles", movieTitle);
    flagCollection.updateOne(existingFlag, flagRemoveOP2);
  }
  else if(movieWithId == null){}
}

/**
 * 
 * @param flagName
 */
public void deleteFlags(String flagName){
  //get flags and movie collection
  MongoCollection<Document> flagCollection = getFlagCollection();
  MongoCollection<Document> movieCollection = getMovieCollection();

  //Filters movies with FlagName
  Bson flagQuery = Filters.eq("flagNames", flagName);
  MongoCursor<Document> movies = movieCollection.find(flagQuery).iterator();

  //iterate through each filtered movie and remove the flag name
  Bson flagRemoveOP = Updates.pull("flagNames", flagName);
  movies.forEachRemaining(document -> {
    flagCollection.updateOne(document, flagRemoveOP);
  });

//set movieTitles array into an emptied one
Bson removeAll = Updates.set("movieTitles", "");
Document flag = flagCollection.find(Filters.eq("flagName", flagName)).first();
flagCollection.updateOne(flag, removeAll);
}

/**
 * 
 * @param movieTitle
 * @param movieId
 */
public void deleteMovie(String movieTitle, String movieId){
  //delete the movie's document
  //get collections
  MongoCollection<Document> movieCollection = getMovieCollection();
  MongoCollection<Document> actorCollection = getActorCollection();
  MongoCollection<Document> reviewCollection = getReviewCollection();
  MongoCollection<Document> flagCollection = getFlagCollection();
  //delete the movie's document
  movieCollection.deleteOne(Filters.eq("id", movieId));
  //filters all actors with the listed movie
  MongoCursor<Document> actors = actorCollection.find(Filters.eq("movies", movieTitle)).iterator();
  Bson movieRemoval = Updates.pull("movies", movieTitle);
  actors.forEachRemaining(document -> {
    // Delete each movie correspond with movieTitle in each qualified actor
    actorCollection.updateOne(document, movieRemoval);
  });
  //delete movie within flags
  MongoCursor<Document> flags = flagCollection.find(Filters.eq("movieTitles", movieTitle)).iterator();
  Bson movieRemovalF = Updates.pull("movieTitles", movieTitle);
  flags.forEachRemaining(document -> {
    // Delete each movie correspond with movieTitle in each qualified actor
    flagCollection.updateOne(document, movieRemovalF);
  });

  //delete all reviews related to the movie
  reviewCollection.deleteMany(Filters.eq("movieTitle", movieTitle));
}

public void deleteActor(String id){
  MongoCollection<Document> actorCollection = getActorCollection();
  actorCollection.deleteOne(Filters.eq("id", id));
}

public void deleteReview(String title, String userName){
  MongoCollection<Document> reviewCollection = getReviewCollection();
  //get all reviews which has the required movie title and userName
  Bson reviewFilter = Filters.and(Filters.eq("movieTitle", title), Filters.eq("userName", userName));
  reviewCollection.deleteMany(reviewFilter);  
}


}
