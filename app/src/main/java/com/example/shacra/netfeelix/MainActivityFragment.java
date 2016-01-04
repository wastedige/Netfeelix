package com.example.shacra.netfeelix;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class MainActivityFragment extends Fragment {

    GridView grid;
    Context currentContext;
    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_main, container, false);
        grid = (GridView) fragView.findViewById(R.id.gridview);
        currentContext = getActivity();

        FetchMovieTask fetcher = new FetchMovieTask(currentContext, grid);
        fetcher.execute();
        // grid.setAdapter(new CustomGridAdapter(getActivity(), imageArray));

        return fragView;
    }





}
