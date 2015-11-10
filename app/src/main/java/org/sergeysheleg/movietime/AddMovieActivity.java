package org.sergeysheleg.movietime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddMovieActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView = null;
    private EditText editText = null;
    private ImageButton imageButton = null;
    private FoundedMoviesListAdapter listAdapter = null;
    public static ArrayList<FoundedMovie> movies = null;

    AlertDialog.Builder ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        ad = new AlertDialog.Builder(this);
        ad.setTitle("Add movie");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = (ListView) findViewById(R.id.foundedMoviesListView);
        listAdapter = new FoundedMoviesListAdapter(this, new ArrayList<FoundedMovie>());
        listView.setAdapter(listAdapter);
        editText = (EditText) findViewById(R.id.searchMovieTitle);
        imageButton = (ImageButton) findViewById(R.id.searchMovieButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movies = OMDbAPI.getInstance().searchMoviesByTitle(editText.getText().toString());
                if(movies == null) {
                    Toast.makeText(v.getContext(), "Movies not found", Toast.LENGTH_SHORT).show();
                } else {
                    listAdapter.setMovies(movies);
                    listAdapter.notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "Founded " + movies.size() + " movies", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(view.getContext(), AboutMovieActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("where", 0);
                        startActivity(intent);
                    }
                });

        listView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                        ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                                SavedMovie m = new SavedMovie(movies.get(position));

                                MainActivity.tabOne.savedMoviesListAdapter.movies.add(m);
                                //MainActivity.tabOne.savedMoviesListAdapter.notifyDataSetChanged();
                            }
                        });
                        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int arg1) {
                            }
                        });
                        ad.show();
                        return false;
                    }
                });


    }
}
