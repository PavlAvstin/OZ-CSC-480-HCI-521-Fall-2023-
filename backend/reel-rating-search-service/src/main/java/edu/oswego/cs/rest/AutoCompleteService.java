package edu.oswego.cs.rest;

import edu.oswego.cs.rest.JsonClasses.Movie;
import jakarta.json.Json;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.List;

@ServerEndpoint(value = "/autocomplete", // "/systemLoad"
        decoders = { AutoCompleteDecoder.class },
        encoders = { AutoCompleteEncoder.class })


public class AutoCompleteService {

    @OnOpen
    public void onOpen(Session session) {
       
    }

    @OnMessage
    public void onMessage(String option, Session session) throws IOException {


        //string option.startswith().substring. if
        //title:
        //then: var movies = movieDatabase.manualSearchByMovieName(option);
        //releasedate
        //then: var movies = movieDatabase.manualSearchReleaseDate(option);
        //Director
        //then: var movies = movieDatabase.manualSearchByDirectyor(option);
        //Cast
        //then: var movies = movieDatabase.manualSearchByMovieCast(option);

        var movieDatabase = new DatabaseController();


        var split = option.split(":"); // cut at colon.
        var searchCommand = split[0];
        var inputForSearch = split[1];

        switch (searchCommand) {
            case "title" -> {
                var movies = movieDatabase.manualSearchByMovieName(inputForSearch);
                sendMovieResults(session, movies);
            }

            case "releasedate" -> {
                var movies = movieDatabase.manualSearchByMovieReleaseDate(inputForSearch);
                sendMovieResults(session, movies);
            }

            case "director" -> {
                var movies = movieDatabase.manualSearchByMovieDirector(inputForSearch);
                sendMovieResults(session, movies);
            }

            case "cast" -> {
                var movies = movieDatabase.manualSearchByMovieCast(inputForSearch);
                sendMovieResults(session, movies);
            }

            case "indexTitle" -> {
                var movies = movieDatabase.searchByMovieNameIndex(inputForSearch);
                sendMovieResults(session, movies);
            }

            case "indexCast" -> {
                var movies = movieDatabase.searchByMovieCastIndex(inputForSearch);
                sendMovieResults(session, movies);
            }
            default -> {
                session.getBasicRemote().sendText("Please do title: , cast: , director: etc.");
            }
        }

    }

    private static void sendMovieResults(Session session, List<Movie> movies){
        var builder = Json.createObjectBuilder();
        //put first 5 movies found in here
        var array = Json.createArrayBuilder();
        //Picks between size and movies.size. In case less than 5 show up.
        var maxIndex = Math.min(5, movies.size());
        for (int i = 0; i < maxIndex; i++) {
            array.add(movies.get(i).getTitle());
        }
        builder.add("results", array);

        var jsonResults = builder.build();
        //Sending the list
        try {
            session.getBasicRemote().sendObject(jsonResults);
        } catch (IOException | EncodeException e) {
            //prints error
            e.printStackTrace();
        }
    }

    //Have a single endpoint but the endpoint reads the beginning part.





}
