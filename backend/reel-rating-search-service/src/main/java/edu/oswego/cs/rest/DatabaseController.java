package edu.oswego.cs.rest;

import java.util.*;

import com.mongodb.client.model.*;
import edu.oswego.cs.rest.JsonClasses.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseController {
  private static String mongoDatabaseName = System.getenv("MONGO_MOVIE_DATABASE_NAME");
  private static String mongoURL = System.getenv("MONGO_MOVIE_URL");
  private static MongoClient mongoClient = MongoClients.create(mongoURL);

  public MongoDatabase getMovieDatabase() {
    return mongoClient.getDatabase(mongoDatabaseName);
  }


  //***********Index for movie title. Repeated call of createIndex multiple times doesn't do anything.
  public void createTitleIndex() {
    var movies = getMovieCollection();
    movies.createIndex(Indexes.text("title"));
  }

  public void createActorNameIndex() {
    var movies = getActorCollection();
    movies.createIndex(Indexes.text("name"));
  }

  // We could have duplicate collections with the same data each of which have a text index for each field. That sounds
  // like a bad idea since it'd make things harder to update and take up more space.
  public MongoCollection<Document> getMovieCollection() {
    return getMovieDatabase().getCollection("movies");
  }

  public MongoCollection<Document> getActorCollection() {
    return getMovieDatabase().getCollection("actors");
  }

  //Takes each individual word and searches for the word and if
  //The partial search automatimally works-Using this as an example
  //"and" will not work. "the" does not work.
  // if input string has "the" and "and", remove them
  // for each word in the string, search it up.
  //searched each individual if, if nothing comes up then it returns nothing.
  //searched by words with the exception of "and" and "the" (lowecase a or uppercase a)
  public List<Movie> searchByMovieNameIndex(String title){
    //Must create a text index before running a text search
    createTitleIndex();
    var moviesToReturn = new ArrayList<Movie>();
    TextSearchOptions options = new TextSearchOptions().caseSensitive(false);
    //Returns iterable of documents
    Bson filter = Filters.text(title, options);
    var movies = getMovieCollection();
    //turning documents into movie objects
    movies.find(filter).forEach(document -> {
      Movie m = documentToMovie(document);
      moviesToReturn.add(m);
    });
    return moviesToReturn;
  }
  //get a list of words to search
  //for each word in the list, we search for the movie.
  //We would search each word individually
  //flaw 1: doesn't show order in which typed. (maybe later)
  //flaw 2: case-sensitive -solved
  //flaw 3: doesn't take commas, periods, or punctuation -supposedly solved
  //flaw 4:Plurals can't be taken -supposedly solved
  //manual search has the one-up on the ONE regard where a word doesn't have to be accurate


  public List<Movie> manualSearchByMovieName(String title) {
    var moviesToReturn = new ArrayList<Movie>();
    var movies = getMovieCollection();
    //The partial part
    String[] words = filterString(title);
    movies.find().forEach(doc -> {
      var movieTitle = doc.getString("title").toLowerCase();
      for (String word : words) {
        if (movieTitle.toLowerCase().contains(word)) {
          var m = documentToMovie(doc);
          moviesToReturn.add(m);
          break; //No duplicates
        }
      }
    });

    moviesToReturn.sort((o1, o2) -> {
      int o1Score = stringSimilarity(title, o1.getTitle());
      int o2Score = stringSimilarity(title, o2.getTitle());

      return Integer.compare(o2Score, o1Score);

    });

    return moviesToReturn;
  }

  private static int stringSimilarity(String title, String o1) {
    //The amount of characters starting from left to right that match the input string.
    var inputLowerCase = title.toLowerCase();
    var o1LowerCase = o1.toLowerCase();
    int maxIndex = Math.min(inputLowerCase.length(), o1LowerCase.length());

    int score = 0;
    for (int i = 0; i < maxIndex; i++) {

      //input character
      var inputC = inputLowerCase.charAt(i);
      var o1C = o1LowerCase.charAt(i);

      if (inputC == o1C) {
        score++;
      }
    }
    return score;
  }

  //Looks to see if it matches by letter.


  private static String[] filterString(String input) {
    String[] notWords = input.toLowerCase().split(" ");
    //Look for the word "and", "the"
    //for each word in the string, take out -es, s, and punctuation
    var suffixes = new String[] {"es", "s"};
    var words = Arrays.stream(notWords)
            .filter(word -> !(word.equals("the") || word.equals("and")))
            .map(str -> str.replaceAll("['.,;:*#&()@!$%^]", "")) // remove punctuation
            .map(str -> {
              for (var suff : suffixes) {
                if (str.endsWith(suff)) {
                  return str.substring(0, str.length() - suff.length());
                }
              }
              return str;
            })
            .toArray(String[]::new);
    return words;
  }

  //enable partial search
  // Then: 2030s = 2031, 2032, 2033..etc (maybe later). idk if requirement
  //Should it take words??
  public List<Movie> manualSearchByMovieReleaseDate(String releaseDate) {
    var moviesToReturn = new ArrayList<Movie>();
    var movies = getMovieCollection();

    String[] words = filterString(releaseDate);

    movies.find().forEach(doc -> {
      var movieDate = doc.getString("releaseDate");
      for (String word : words) {
        if (movieDate.toLowerCase().contains(word)) {
          var m = documentToMovie(doc);
          moviesToReturn.add(m);
          break; //No duplicates
        }
      }
    });

    moviesToReturn.sort((o1, o2) -> {
      int o1Score = stringSimilarity(releaseDate, o1.getReleaseDate());
      int o2Score = stringSimilarity(releaseDate, o2.getReleaseDate());

      return Integer.compare(o2Score, o1Score);

    });

    return moviesToReturn;
  }

  public List<Movie> manualSearchByMovieDirector(String director) {
    var moviesToReturn = new ArrayList<Movie>();
    var movies = getMovieCollection();

    String[] words = filterString(director);

    movies.find().forEach(doc -> {
      var movieDirector = doc.getString("director");
      for (String word : words) {
        if (movieDirector.toLowerCase().contains(word)) {
          var m = documentToMovie(doc);
          moviesToReturn.add(m);
          break; //No duplicates
        }
      }
    });

    moviesToReturn.sort((o1, o2) -> {
      int o1Score = stringSimilarity(director, o1.getDirector());
      int o2Score = stringSimilarity(director, o2.getDirector());

      return Integer.compare(o2Score, o1Score);

    });

    return moviesToReturn;
  }

  //Get actor name ->actorid -> list of movies
  //actor name (anything) ->List of actor names ->list of actor ids -> a list of movies
  public List<Movie> manualSearchByMovieCast(String cast) {

    var moviesToReturn = new ArrayList<Movie>();
    var actorsToSearch = new ArrayList<Actor>();
    var actorsCollection = getActorCollection();
    var moviesCollection = getMovieCollection();

    String[] words = filterString(cast);

    //Looking into actor collection
    actorsCollection.find().forEach(a->{
      //if the input name has an actor associated with it, get the movieID associated with it.
      for (String word : words) {
        if (a.getString("name").toLowerCase().contains(word)){
          var actorMovies = a.getList("movies", String.class);
          actorMovies.forEach(id->{
            var objectId = new ObjectId(id);
            var filter = Filters.eq("_id", objectId);
            //movieIds to movie objects
            moviesCollection.find(filter).forEach(m->{
              var movie = documentToMovie(m);
              moviesToReturn.add(movie);
            });
          });
        }
      }
    });

    moviesToReturn.sort((o1, o2) -> {
      int o1Score = stringSimilarity(cast, o1.getPrincipleCast());
      int o2Score = stringSimilarity(cast, o2.getPrincipleCast());

      return Integer.compare(o2Score, o1Score);

    });

    return moviesToReturn;
  }


  //Index for actors
  //Another check to specialize it.
  //Auto-complete stuff.
  public List<Movie> searchByMovieCastIndex(String cast) {

    var moviesToReturn = new ArrayList<Movie>();
    var actorsCollection = getActorCollection();
    var moviesCollection = getMovieCollection();

    //Must create a text index before running a text search
    createActorNameIndex();
    var options = new TextSearchOptions().caseSensitive(false);
    Bson filter = Filters.text(cast, options);
    actorsCollection.find(filter).forEach(a -> {
      var actorMovies = a.getList("movies", String.class);
      actorMovies.forEach(id -> {
        var objectId = new ObjectId(id);
        var eqFilter = Filters.eq("_id", objectId);
        moviesCollection.find(eqFilter).forEach(m -> {
          var movie = documentToMovie(m);
          moviesToReturn.add(movie);
        });
      });
    });

    return moviesToReturn;
  }


//Partial search.
  private static Movie documentToMovie(Document document) {
    var m = new Movie();
    m.setDirector(document.getString("director"));
    m.setRuntime(document.getString("runtime"));
    m.setSummary(document.getString("plotSummary"));
    m.setTitle(document.getString("title"));
    m.setWriters(document.getString("writers"));
    m.setReleaseDate(document.getString("releaseDate"));
    m.setId(document.getObjectId("_id").toHexString());
    return m;
  }
}