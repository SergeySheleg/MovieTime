package org.sergeysheleg.movietime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class TabTwoFragment extends Fragment {
    ListView movieListView;
    SavedMoviesListAdapter savedMoviesListAdapter;

    private int tabNumber = 2;

    public void setTabNumber(int tabNumber) {
        this.tabNumber = tabNumber;
    }

    public TabTwoFragment() {}

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
        return inflater.inflate(R.layout.fragment_tab_two, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieListView = (ListView) getActivity().findViewById(R.id.savedMoviesListViewTabTwo);
        savedMoviesListAdapter = new SavedMoviesListAdapter(getActivity(), new ArrayList<SavedMovie>());
        movieListView.setAdapter(savedMoviesListAdapter);

        movieListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), AboutMovieActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("where", tabNumber);
                        startActivity(intent);
                    }
                });
    }
}
