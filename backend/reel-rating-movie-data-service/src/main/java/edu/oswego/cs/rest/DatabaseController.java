package edu.oswego.cs.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.mongodb.client.model.Sorts.descending;

import edu.oswego.cs.rest.JsonClasses.*;
import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

public class DatabaseController {
  private static String mongoDatabaseName = System.getenv("MONGO_MOVIE_DATABASE_NAME");
  private static String mongoURL = System.getenv("MONGO_MOVIE_URL");
  private static MongoClient mongoClient = MongoClients.create(mongoURL);

  public MongoDatabase getMovieDatabase() {
    return mongoClient.getDatabase(mongoDatabaseName);
  }

  /*
   * get[DatabaseEntity]Collection methods return the specified collection of entities. These collections can then
   * be queried and updated by the other CRUD operations.
   */
  public MongoCollection<Document> getTagCollection() {
    return getMovieDatabase().getCollection("tags");
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


  public MongoCollection<Document> getReviewCollection() {
    return getMovieDatabase().getCollection("reviews");
  }

  /*
   * Image methods
   *
   * These are used to store, edit, and retrieve images to display within the application. Due to MongoDB's
   * approach to storing images collections cannot be used. Instead, GridFSBuckets are used and Mongo handles the
   * underlying splitting and storing of data.
   *
   * getStockImageBucket
   * storeStockImages
   * getRandomImageId
   * getStockImage
   * getMovieImageId
   */

  // total number of stock images being stored. Used to grab an image at random.
  int numMovieImages = 3;

  public GridFSBucket getStockImageBucket() {
    return GridFSBuckets.create(getMovieDatabase(), "stockMovieImages");
  }

  /**
   * Stores pre-selected stock images in the database to be displayed for movies
   */
  public void storeStockImages() {
    GridFSBucket gridFSBucket = getStockImageBucket();
    for (int i = 1; i <= numMovieImages; i++) {
      // create a name to store the image
      String movieFileName = "stockImage" + i + ".webp";
      String movieImagePath = "images/" + movieFileName;
      // attempt to grab and upload the image
      try {
        File file = new File(this.getClass().getClassLoader().getResource(movieImagePath).getFile());
        InputStream image = new FileInputStream(file);
        gridFSBucket.uploadFromStream(movieFileName, image);
      // if the image file cannot be found
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Selects and returns an image id at random from the established image database.
   * @return the hex String representation of an image from the gridSFBucket
   */
  public String getRandomImageId() {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    // Account for movieImages starting at an index of 1.
    int movieNumber = random.nextInt(numMovieImages) + 1;
    String movieFileName = "stockImage" + movieNumber + ".webp";
    GridFSBucket gridFSBucket = getStockImageBucket();
    Bson query = Filters.eq("filename", movieFileName);
    return gridFSBucket.find(query).first().getObjectId().toHexString();
  }

  /**
   * Gets image from the stock image database using the given hexId.
   * @param hexId Unique String hexId of the desired image
   * @return Single Byte[] representation of the image from the GridSFBucket
   */
  public byte[] getStockImage(String hexId) {
    // establish the images hex id and image bucket
    ObjectId stockImageId = new ObjectId(hexId);
    GridFSBucket gridFSBucket = getStockImageBucket();

    // open the download stream and grab the image
    GridFSDownloadStream downloadImageStream = gridFSBucket.openDownloadStream(stockImageId);
    int fileLength = (int)downloadImageStream.getGridFSFile().getLength();
    byte[] imageBytes = new byte[fileLength];
    downloadImageStream.read(imageBytes);
    return imageBytes;
  }

  /**
   * Returns the image assigned to the given movie
   * @param movieId MongoDB assigned unique String hexId of the movie to search by
   * @return String hexId of the image related to the provided movie
   */
  public String getMovieImageId(String movieId) {
    Document movieDocument = getMovieDocumentWithHexId(movieId);
    if (movieDocument == null) return null;
    return movieDocument.getString("movieImageId");
  }

  /*
   * Create Operations
   *
   * The following methods are used in the initial database population and also exist in their respective microservice.
   *
   * createTag
   * createRating
   * createReview
   * createActor
   * createMovie
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
    Bson filter = Filters.and(
            Filters.eq("upperbound", upperbound),
            Filters.eq("ratingName", ratingName),
            Filters.eq("username", username),
            Filters.eq("movieId", movieIdHexString)
    );

    Document rating = ratingCollection.find(filter).first();
    // attempt to get the corresponding movie
    Document movie = getMovieDocumentWithHexId(movieIdHexString);

    // check to see if movie exists and rating is not already created by user
    if (rating == null && movie != null) {
      Document newRating = new Document("username", username.toLowerCase())
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
    if(rating != null){
      Bson updateOperation = Updates.set("userRating", userRating);
      ratingCollection.updateOne(filter, updateOperation);
    }
  }

  /**
   * Creates and stores a review in the database. Reviews are the freeform text user generated data. Users are not
   * allowed to add a review for a movie that does not exist. Users are currently allowed to make multiple reviews
   * for the same movie.
   *
   * @param reviewDescription freeform text from the user. No limits in size.
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
  }

  /**
   * Create and adds a movie to the database. Allows for the addition of two movies by the same name.
   *
   * @param movieTitle Title of movie. For example "Star Wars: Attack of the Clones" or "The Bee Movie"
   * @param director Director of the movie.
   * @param releaseDate Release date of the movie
   * @param runtime Movies runtime (in minutes?)
   * @param writers List of writers who worked on the movie
   * @param plotSummary Short description of the movie or its plot
   */
  public void createMovie(String movieTitle, String director, String releaseDate,
                          String runtime, String writers, String plotSummary){
    // get collections
    MongoCollection<Document> movieCollection = getMovieCollection();

    Document newMovie = new Document().append("title", movieTitle).append("director", director).append("writers", writers)
            .append("releaseDate", releaseDate).append("runtime", runtime).append("plotSummary", plotSummary)
            .append("movieImageId", getRandomImageId());
    movieCollection.insertOne(newMovie);
  }

  /*
   * Get functions
   *
   * getMoviesWithFilter
   * getTagsWithFilter
   *
   * getMoviesWithTag
   * getMoviesWithRatingCategory
   * getMoviesWithRatingCategory
   * getMoviesWithActor
   * getMoviesWithTitle
   * getMoviesWithMovieId
   * getMovieWithTitle
   * getMovieDocumentWithHexId
   *
   * getMoviesWithMostReviews
   * getRecentReleaseMovies
   * getMostPopularAggregatedRatingForMovie
   *
   * getThreeTags
   * getTagsByMovieId
   */
  /**
   * <p>Get with filter operations allow for mutable searches within the database. These functions are called internally
   * by the <code>getXWithY</code> where X is a database entity and Y is a another database entity or field. </p>
   *
   * <p>For example the <code>getMoviesWithFilter()</code> method is used by the <code>getMoviesWithTag()</code>
   * method</p> to return all the movies that have the specified tag.
   */
    private static ArrayList<Movie> getMoviesWithFilter(MongoCollection<Document> moviesCollection, Bson filter) {
    var movies = moviesCollection.find(filter).map(document -> {
      var m = new Movie();
      m.setDirector(document.getString("director"));
      m.setRuntime(document.getString("runtime"));
      m.setSummary(document.getString("plotSummary"));
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

  /**
   * <p>Get tags with filter operations allow for mutable searches within the database. These functions are called
   * internally by the <code>getXWithY</code> where X is a database entity and Y is a another database entity or field. </p>
   *
   * <p>For example the <code>getMoviesWithFilter()</code> method is used by the <code>getMoviesWithTag()</code>
   * method</p> to return all the movies that have the specified tag.
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
      return tag;
    });
    var list = new ArrayList<Tag>();
    ratings.forEach(list::add);
    return list;
  }

  /**
   * get[DatabaseEntity]With[Parameter] methods are used to retrieve database entities by using another entity or a
   * given parameter. These make use of the get[DatabaseEntity]WithFilter methods.
   *
   */
  public List<Movie> getMoviesWithTag(String tag) {
    var moviesCollection = getMovieCollection();
    var filter = Filters.eq("tagNames", tag);
    return getMoviesWithFilter(moviesCollection, filter);
  }

  public List<Movie> getMoviesWithRatingCategory(String ratingCategory) {
    var moviesCollection = getMovieCollection();
    var filter = Filters.eq("ratingCategoryNames", ratingCategory);
    return getMoviesWithFilter(moviesCollection, filter);
  }

  public List<Movie> getMoviesWithRatingCategory(String ratingName, String upperbound) {
    var ratingNameFilter = Filters.eq("ratingName", ratingName);
    var upperboundFilter = Filters.eq("upperbound", upperbound);
    var filter = Filters.and(ratingNameFilter, upperboundFilter);
    var ratings = getRatingCollection();
    var movieCollection = getMovieCollection();
    MongoIterable<Movie> movieIterable = ratings.distinct("movieId",filter, String.class)
    .map(movieId -> {
      ObjectId movieIdObject = new ObjectId(movieId);
      Document document = movieCollection.find(Filters.eq("_id", movieIdObject)).first();
      var m = new Movie();
      m.setDirector(document.getString("director"));
      m.setRuntime(document.getString("runtime"));
      m.setSummary(document.getString("plotSummary"));
      m.setTitle(document.getString("title"));
      m.setWriters(document.getString("writers"));
      m.setReleaseDate(document.getString("releaseDate"));
      m.setId(document.getObjectId("_id").toHexString());
      return m;
    });
    List<Movie> movies = new ArrayList<>();
    movieIterable.forEach(movies::add);
    return movies;
  }

  public List<Movie> getMoviesWithActor(String actorId) {
    var moviesCollection = getMovieCollection();
    var filter = Filters.eq("principalCast", actorId);
    return getMoviesWithFilter(moviesCollection, filter);
  }

  public List<Movie> getMoviesWithTitle(String title) {
    var moviesCollection = getMovieCollection();
    var filter = Filters.eq("title", title);
    return getMoviesWithFilter(moviesCollection, filter);
  }

  public Optional<Movie> getMovieWithMovieId(String hexID) {
    var moviesCollection = getMovieCollection();
    ObjectId movieId = new ObjectId(hexID);
    var filter = Filters.eq("_id", movieId);
    return getMoviesWithFilter(moviesCollection, filter).stream().findFirst();
  }

  public Optional<Movie> getMovieWithTitle(String title){
    return getMoviesWithTitle(title).stream().findFirst();
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
  /**
   * returns to numMovies most recent movies based on the year of their release. This is done by sorting the
   * ordering the collection by date and returning the first numMovies.
   */
  public List<Movie> getRecentReleaseMovies(int numMovies) {
    // ArrayList to store Movie objects and eventually return
    List<Movie> recentReleaseMovies = new ArrayList<>();
    // get the movie collection
    MongoCollection<Document> movieCollection = getMovieCollection();
    // sort the entire collection
    MongoIterable<Document> sortedList = movieCollection.find().sort(descending("releaseDate")).limit(numMovies);

    // for each of the documents make a new movie and add to the ArrayList to return
    for ( Document d : sortedList) {
      // create the new movie object
      Movie m = new Movie();
      // set the needed information from movie
      m.setId(d.getObjectId("_id").toHexString());
      m.setTitle(d.getString("title"));
      m.setSummary(d.getString("plotSummary"));

      // add the movie to the list to return
      recentReleaseMovies.add(m);
    }

    return recentReleaseMovies;
  }


  /**
   * Gets the first numMovies movies that have the most reviews from the database.
   * @param numMovies the specified number of movies to be returned
   * @return A list of movies in descending order of most reviewed.
   */
  public List<Movie> getMoviesWithMostReviews(int numMovies) {
    MongoCollection<Document> reviews = getReviewCollection();
    MongoCollection<Document> movieCollection = getMovieCollection();
    MongoIterable<Movie> reviewsAggregated = reviews.aggregate(
      Arrays.asList(
        Aggregates.group("$movieId", Accumulators.sum("count", 1)),
        Aggregates.sort(Sorts.descending("count")),
        Aggregates.limit(numMovies)
      )
    ).map(doc -> {
      Movie movie = new Movie();
      movie.setId(doc.getString("_id"));
      ObjectId id = new ObjectId(movie.getId());
      // get the movie that matches the movie id
      Document movieDoc = movieCollection.find(Filters.eq("_id", id)).first();
      // set the needed information from movie
      movie.setTitle(movieDoc.getString("title"));
      movie.setSummary(movieDoc.getString("plotSummary"));
      return movie;
    });
    List<Movie> movies = new ArrayList<>();
    reviewsAggregated.forEach(movies::add);
    return movies;
  }

  /**
   * Finds the rating category with the most ratings, and returns the most popular upperbound of this subset, for the
   * provided movieId. It also calculates the average rating for the rating cateogyr found to be most popular.
   * @param movieId MongoDB hexId of the movie to search the ratings of
   * @return a Rating object with the most popular rating name and upperbound and its average.
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

    // return null immediately if ratingNameDoc is null
    if (ratingNameDoc == null) {
      return null;
    }

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
//
//    // create a rating object that has the most popular name, upperbound, and a userRating of the average of all
//    //  the ratings of that name with that upperbound.
//    //  TODO consider collecting for all ratings of this name which would take some normalizing. Is this worth it?
    Rating rating = new Rating();
    rating.setRatingName(mostPopularCategoryName);
    rating.setUpperbound(mostPopularCategoryUpperbound);
    rating.setUserRating(Double.toString(average));
    return rating;
  }

  /**
   * Grabs three tags from the specified movie. Used in the getRecentReleaseMovies and in a larger sense for getting
   * the information needed to display the movie preview (movie title, movie summary, name of three tags, and
   * aggregated most popular rating).
   *
   * @param movieId MongoDB unique hex id of the movie to get the tags from
   * @return a list of tags length three
   */
  public List<Tag> getThreeTags(String movieId) {
    List<Tag> tags = getTagsByMovieId(movieId);
    return tags.subList(0, tags.size() < 3 ? tags.size() : 3);
  }

  /**
   * Returns all tags containing the provided movie hexId
   * @param movieId MongoDB hexId of movie to get tags from
   * @return List tag objects associated with the movie
   */
  public List<Tag> getTagsByMovieId(String movieId) {
    var reviews = getTagCollection();
    var filter = Filters.eq("movieId", movieId);
    return getTagsWithFilter(reviews, filter);
  }
}