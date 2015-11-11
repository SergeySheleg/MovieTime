package org.sergeysheleg.movietime;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SavedMovie {
    private String _title = null;
    private String _year = null;
    private String _imdbID = null;
    private String _genre = "";
    private String _director = "";
    private String _plot = "";
    private String _imdbRating = "0.0";
    private float _userRating = 0;
    private Bitmap _posterBitmap = null;

    public SavedMovie(FoundedMovie foundedMovie){
        _title = foundedMovie.getTitle();
        _year = foundedMovie.getYear();
        _imdbID = foundedMovie.getImdbID();
        _posterBitmap = foundedMovie.getPosterBitmap();

        DownloadHelper downloadHelper = new DownloadHelper().setType(DownloadHelper.Type.JSONObject);
        String url = "http://www.omdbapi.com/?i=" + _imdbID + "&plot=short&r=json";
        try {
            downloadHelper.execute(url).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JSONObject object = downloadHelper.getJSONObject();
        try {
            _genre = object.getString("Genre");
            _director = object.getString("Director");
            _plot = object.getString("Plot");
            _imdbRating = "IMDb Rating: " + object.getString("imdbRating") + "/10";
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDirector() {
        return _director;
    }

    public String getGenre() {
        return _genre;
    }

    public String getIMDbID() {
        return _imdbID;
    }

    public String getIMDbRating() {
        return _imdbRating;
    }

    public String getPlot() {
        return _plot;
    }

    public Bitmap getPosterBitmap() {
        return _posterBitmap;
    }

    public String getTitle() {
        return _title;
    }

    public float getUserRating() {
        return _userRating;
    }

    public String getYear() {
        return _year;
    }

    public void setUserRating(float userRating) {
        this._userRating = userRating;
    }
}
