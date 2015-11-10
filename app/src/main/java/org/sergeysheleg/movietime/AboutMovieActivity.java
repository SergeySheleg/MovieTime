package org.sergeysheleg.movietime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class AboutMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_movie);

        int position = getIntent().getIntExtra("position", 0);
        int fromWhere = getIntent().getIntExtra("where", 0);

        SavedMovie movie = null;
        switch(fromWhere) {
            case 0: {
                movie = new SavedMovie(AddMovieActivity.movies.get(position));
            } break;

            case 1: {
                movie = MainActivity.tabOne.savedMoviesListAdapter.movies.get(position);
            } break;

            case 2: {
                movie = MainActivity.tabTwo.savedMoviesListAdapter.movies.get(position);
            } break;

            default: return;
        }

        TextView textView;
        ImageView imageView;
        RatingBar userRatingBar;

        textView = (TextView) this.findViewById(R.id.aboutMovieTitle);
        textView.setText(movie.getTitle());

        textView = (TextView) this.findViewById(R.id.aboutMovieIMDbRating);
        textView.setText(movie.getIMDbRating());

        imageView = (ImageView) this.findViewById(R.id.aboutMoviePoster);
        imageView.setImageBitmap(movie.getPosterBitmap());

        textView = (TextView) this.findViewById(R.id.aboutMovieGenre);
        textView.setText(movie.getGenre());

        textView = (TextView) this.findViewById(R.id.aboutMovieYear);
        textView.setText(movie.getYear());

        textView = (TextView) this.findViewById(R.id.aboutMovieDirector);
        textView.setText(movie.getDirector());

        textView = (TextView) this.findViewById(R.id.aboutMoviePlot);
        textView.setText(movie.getPlot());

        userRatingBar = (RatingBar) this.findViewById(R.id.aboutMovieUserRating);
        userRatingBar.setRating(movie.getUserRating());
        if(fromWhere == 0) {
            userRatingBar.setEnabled(false);
        }
    }
}
