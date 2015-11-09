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
        String url = new String("http://www.omdbapi.com/?s=" + movieTitle + "&y=&plot=full&r=json&type=movie");
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
        }

        ArrayList<FoundedMovie> movies = new ArrayList<FoundedMovie>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                movies.add(new FoundedMovie(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
}
