package org.sergeysheleg.movietime;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TabFragment extends Fragment {
    ListView movieListView;
    SavedMoviesListAdapter savedMoviesListAdapter;

    private int tab_number = 0;
    private int content = 0;
    private View rootView = null;


    public TabFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        savedMoviesListAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        tab_number = args.getInt("tab_number");
        content = args.getInt("tab_layout");

        rootView = inflater.inflate(content, container, false);

        movieListView = (ListView) rootView.findViewById(R.id.savedMoviesListView);
        savedMoviesListAdapter = new SavedMoviesListAdapter(getActivity(), new ArrayList<SavedMovie>());
        movieListView.setAdapter(savedMoviesListAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        movieListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), AboutMovieActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("where", tab_number);
                        startActivity(intent);
                    }
                });

        movieListView.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                    ad.setTitle("Delete");
                    ad.setMessage(savedMoviesListAdapter.movies.get(position).getTitle());
                    ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            savedMoviesListAdapter.movies.remove(position);
                            savedMoviesListAdapter.notifyDataSetChanged();
                        }
                    });
                    ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) { }
                    });
                    ad.show();
                    return true;
                }
            });
    }
}
