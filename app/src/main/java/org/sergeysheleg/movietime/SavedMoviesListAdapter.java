package org.sergeysheleg.movietime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class SavedMoviesListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<SavedMovie> movies;

    public SavedMoviesListAdapter(Context context, ArrayList<SavedMovie> movies) {
        this.context = context;
        this.movies = movies;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            view = layoutInflater.inflate(R.layout.saved_movies_list_item, parent, false);
        }

        SavedMovie movie = (SavedMovie) getItem(position);

        ((TextView) view.findViewById(R.id.savedMovieTitle)).setText(movie.getTitle());
        ((TextView) view.findViewById(R.id.savedMovieGenre)).setText(movie.getGenre());
        ((TextView) view.findViewById(R.id.savedMovieYear)).setText(movie.getYear());
        ((TextView) view.findViewById(R.id.savedMovieIMDbRating)).setText(movie.getIMDbRating());
        ((TextView) view.findViewById(R.id.savedMovieUserRating)).setText("Your: " + String.valueOf(movie.getUserRating()) + "/5");
        ((ImageView) view.findViewById(R.id.savedMoviePoster)).setImageBitmap(movie.getPosterBitmap());
        return view;
    }
}
