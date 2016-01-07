package com.example.shacra.netfeelix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


public class MainActivityFragment extends Fragment {

    GridView grid;
    Context currentContext;
    FetchMovieTask fetcher;
    public MainActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Reference -- http://developer.android.com/guide/topics/ui/menus.html
//        Log.v("OPTIONS", item. + "");
        switch (item.toString()) {
            case "Popularity":
                Log.v("OPTIONS", "Popularity");
                fetcher = new FetchMovieTask(currentContext, grid);
                fetcher.execute("sort_by=popularity.desc");
                return true;
            case "Highest R rated":
                fetcher = new FetchMovieTask(currentContext, grid);
                fetcher.execute("certification_country=US&certification=R&sort_by=vote_average.desc");
                Log.v("OPTIONS", "Highest R rated");
                return true;
            default:
                Log.v("OPTIONS", "This should not be happening");


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View fragView = inflater.inflate(R.layout.fragment_main, container, false);
        grid = (GridView) fragView.findViewById(R.id.gridview);
        currentContext = getActivity();

        fetcher = new FetchMovieTask(currentContext, grid);
        fetcher.execute("sort_by=popularity.desc");

        // grid.setAdapter(new CustomGridAdapter(getActivity(), imageArray));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(currentContext, "item" + i, Toast.LENGTH_LONG).show();
                FetchMovieDetailTask(i);
            }
        });
        return fragView;
    }

    public void FetchMovieDetailTask(int pos) {
        if (fetcher.forecastJsonStr != null) {
            Intent intent = new Intent(currentContext, MovieDetailActivity.class);
            intent.putExtra("movieJson", fetcher.forecastJsonStr);
            intent.putExtra("position", pos);
            startActivity(intent);
        }

    }





}
