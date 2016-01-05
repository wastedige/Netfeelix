package com.example.shacra.netfeelix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragView = inflater.inflate(R.layout.fragment_main, container, false);
        grid = (GridView) fragView.findViewById(R.id.gridview);
        currentContext = getActivity();

        fetcher = new FetchMovieTask(currentContext, grid);
        fetcher.execute();
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
