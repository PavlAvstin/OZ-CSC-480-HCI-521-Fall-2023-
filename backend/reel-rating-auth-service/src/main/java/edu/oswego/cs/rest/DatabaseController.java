package edu.oswego.cs.rest;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class DatabaseController {

    String mongaCredDatabaseName = System.getenv("MONGO_CRED_DATABASE_NAME");
    String mongoMovieDatabaseName = System.getenv("MONGO_MOVIE_DATABASE_NAME");
    String mongoActorDatabaseName = System.getenv("MONGO_ACTOR_DATABASE_NAME");
    String mongoReviewDatabaseName = System.getenv("MONGO_REVIEW_DATABASE_NAME");
    String mongoRatingDatabaseName = System.getenv("MONGO_RATING_DATABASE_NAME");
    String mongoURL = System.getenv("MONGO_CRED_URL");

    public MongoDatabase getUserCredentialsDatabase() {
        MongoClient mongoClient = MongoClients.create(mongoURL);
        return mongoClient.getDatabase(mongaCredDatabaseName);
    }

    public MongoDatabase getMovieDatabase() {
        MongoClient mongoClient = MongoClients.create(mongoURL);
        return mongoClient.getDatabase(mongoMovieDatabaseName);
    }

    public MongoDatabase getActorDatabase() {
        MongoClient mongoClient = MongoClients.create(mongoURL);
        return mongoClient.getDatabase(mongoActorDatabaseName);
    }

    public MongoDatabase getReviewDatabase() {
        MongoClient mongoClient = MongoClients.create(mongoURL);
        return mongoClient.getDatabase(mongoReviewDatabaseName);
    }

    public MongoDatabase getRatingDatabase() {
        MongoClient mongoClient = MongoClients.create(mongoURL);
        return mongoClient.getDatabase(mongoRatingDatabaseName);
    }

    public MongoCollection<Document> getUserCollection() {
        return getUserCredentialsDatabase().getCollection("users");
    }

    public MongoCollection<Document> getMovieCollection() {
        return getMovieDatabase().getCollection("movies");
    }

    public MongoCollection<Document> getActorCollection() {
        return getActorDatabase().getCollection("actors");
    }

    public MongoCollection<Document> getReviewCollection() {
        return getReviewDatabase().getCollection("reviews");
    }

    public MongoCollection<Document> getRatingsCollection() {
        return getRatingDatabase().getCollection("actors");
    }

    public void createUser(String username, String password, String sessionId, String dateTime) {
        var users = getUserCollection();
        var userDocument = new Document();
        userDocument.put("username", username);
        userDocument.put("password", password);
        userDocument.put("sessionId", sessionId);
        userDocument.put("dateTime", dateTime);
        users.insertOne(userDocument);
    }

    public boolean checkIfUserExists(String username) {
        MongoCollection<Document> users = getUserCollection();
        return null != users.find(Filters.eq("username", username)).first();
    }

    public void setUserSessionId(String username, String sessionId) {
        MongoCollection<Document> users = getUserCollection();
        Bson filter = Filters.eq("username", username);
        Bson updateOperation = Updates.set("sessionId", sessionId);
        users.updateOne(filter, updateOperation);
    }

    public String getUsername(String sessionId) {
        MongoCollection<Document> users = getUserCollection();
        Bson filter = Filters.eq("sessionId", sessionId);
        return users.find(filter).first().getString("username");
    }

    public String getPassword(String username) {
        MongoCollection<Document> users = getUserCollection();
        Bson filter = Filters.eq("username", username);
        return users.find(filter).first().getString("password");
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
            re.setMovieTitle(document.getString("movieTitle"));

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

    public List<Rating> getUserAssociatedRatings(User user) {
        var ratings = getRatingsCollection();
        var filter = Filters.eq("user", user.getUsername());
        return getRatingsWithFilter(ratings, filter);
    }

    public List<Rating> getRatingsInRatingsCategory(String category) {
        var ratings = getRatingsCollection();
        var filter = Filters.eq("category", category);
        return getRatingsWithFilter(ratings, filter);
    }

    public List<Review> getReviewsByMovieName(String movieName) {
        var reviews = getReviewCollection();
        var filter = Filters.eq("movieName", movieName);
        return getReviewsWithFilter(reviews, filter);
    }

    public List<Review> getReviewsByUser(User user) {
        var reviews = getReviewCollection();
        var filter = Filters.eq("user", user.getUsername());
        return getReviewsWithFilter(reviews, filter);
    }
}

