package org.sergeysheleg.movietime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class FoundedMoviesListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<FoundedMovie> movies;

    public FoundedMoviesListAdapter(Context context,
                                    ArrayList<FoundedMovie> movies) {
        this.context = context;
        this.movies = movies;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMovies(ArrayList<FoundedMovie> movies) {
        this.movies = movies;
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
            view = layoutInflater.inflate(R.layout.founded_movie_list_item, parent, false);
        }

        FoundedMovie movie = (FoundedMovie) getItem(position);

        ((TextView) view.findViewById(R.id.foundedMovieTitle)).setText(movie.getTitle());
        ((TextView) view.findViewById(R.id.foundedMovieYear)).setText(movie.getYear());
        ((ImageView) view.findViewById(R.id.foundedMoviePoster)).setImageBitmap(movie.getPosterBitmap());
        return view;
    }
}

