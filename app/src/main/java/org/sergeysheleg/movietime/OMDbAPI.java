package org.sergeysheleg.movietime;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class OMDbAPI {
    private static OMDbAPI ourInstance = new OMDbAPI();
    public static OMDbAPI getInstance() {
        return ourInstance;
    }

    private OMDbAPI() {
    }

    public ArrayList<FoundedMovie> searchMoviesByTitle(String movieTitle) {
        movieTitle = movieTitle.replace(' ', '+');
        String url = new String("http://www.omdbapi.com/?s=" + movieTitle + "&plot=full&r=json&t=movie&t=series");
        DownloadHelper dh = new DownloadHelper().setType(DownloadHelper.Type.JSONObject);
        try {
            dh.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = null;
        try {
            jsonArray = dh.getJSONObject().getJSONArray("Search");
        } catch (JSONException e) {
            e.printStackTrace();
            jsonArray = null;
        }

        ArrayList<FoundedMovie> movies = null;
        if(jsonArray != null) {
            movies = new ArrayList<FoundedMovie>();
            for(int i = 0; i < jsonArray.length(); i++) {
                try {
                    movies.add(new FoundedMovie(jsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return movies;
    }
}
