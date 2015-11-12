package org.sergeysheleg.movietime;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class FoundedMovie {
    private String _title = null;
    private String _year = null;
    private String _imdbID = null;
    private Bitmap _posterBitmap = null;
    private String _posterURL = null;

    public static Bitmap _noPoster = null;

    public FoundedMovie(final JSONObject object) throws JSONException {
        _title = object.getString("Title");
        _year = object.getString("Year");
        _imdbID = object.getString("imdbID");
        _posterURL = object.getString("Poster");
        /*if( !(_posterURL == null || _posterURL.isEmpty() || _posterURL.equals("N/A")) ) {
            DownloadHelper dh = new DownloadHelper().setType(DownloadHelper.Type.Bitmap);
            try {
                dh.execute(_posterURL).get();
                _posterBitmap = dh.getBitmap();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            _posterBitmap = _noPoster;
        }*/
    }

    public static void setNoPoster(Bitmap _noPoster) {
        FoundedMovie._noPoster = _noPoster;
    }

    public String getTitle() {
        return _title;
    }

    public String getPosterURL() {
        return _posterURL;
    }

    public void setPosterBitmap(Bitmap poster) {
        _posterBitmap = poster;
    }
    public String getYear() {
        return _year;
    }

    public Bitmap getPosterBitmap() {
        return _posterBitmap;
    }

    public String getImdbID() {
        return _imdbID;
    }
}
