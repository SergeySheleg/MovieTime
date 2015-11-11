package org.sergeysheleg.movietime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SearchMovieActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView = null;
    private EditText editText = null;
    private ImageButton imageButton = null;
    private FoundedMoviesListAdapter listAdapter = null;
    public static ArrayList<FoundedMovie> movies = null;
    View rootView = null;
    JSONArray jsonArray = null;

    AlertDialog.Builder ad;

    public class AsyncSearch extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            params[0] = params[0].replace(' ', '+');
            String urlToDownload = new String("http://www.omdbapi.com/?s=" + params[0] + "&plot=full&r=json&t=movie&t=series");

            InputStream inputStream = null;
            HttpURLConnection httpURLConnection = null;
            JSONObject jsonResult = null;
            try {
                URL url = new URL(urlToDownload);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();

                String inputSting;
                while ((inputSting = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputSting);
                }
                jsonResult = new JSONObject(stringBuilder.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            try {
                jsonArray = jsonResult.getJSONArray("Search");
            } catch (JSONException e) {
                e.printStackTrace();
                jsonArray = null;
            }

            return jsonArray.length();
        }

        @Override
        protected void onPreExecute() {
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result == 0) {
                Toast.makeText(rootView.getContext(), "Movies not found", Toast.LENGTH_SHORT).show();
            } else {
                movies = new ArrayList<FoundedMovie>();
                for(int i = 0; i < jsonArray.length(); i++) {
                    try {
                        movies.add(new FoundedMovie(jsonArray.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                listAdapter.setMovies(movies);
                listAdapter.notifyDataSetChanged();
                Toast.makeText(rootView.getContext(), "Founded " + movies.size() + " movies", Toast.LENGTH_SHORT).show();
            }

            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

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
                rootView = v;
                new AsyncSearch().execute(editText.getText().toString());

                /*movies = OMDbAPI.getInstance().searchMoviesByTitle(editText.getText().toString());
                if(movies == null) {
                    Toast.makeText(v.getContext(), "Movies not found", Toast.LENGTH_SHORT).show();
                } else {
                    listAdapter.setMovies(movies);
                    listAdapter.notifyDataSetChanged();
                    Toast.makeText(v.getContext(), "Founded " + movies.size() + " movies", Toast.LENGTH_SHORT).show();
                }*/
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
                        ad.setPositiveButton("Want to watch", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SavedMovie m = new SavedMovie(movies.get(position));
                                MainActivity.tabTwo.savedMoviesListAdapter.movies.add(m);
                            }
                        });

                        ad.setNegativeButton("Already watched", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SavedMovie m = new SavedMovie(movies.get(position));
                                MainActivity.tabOne.savedMoviesListAdapter.movies.add(m);
                            }
                        });

                        ad.show();
                        return true;
                    }
                });
    }
}
