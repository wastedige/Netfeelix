package com.example.shacra.netfeelix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/**
 * Created by shacra on 12/31/2015.
 */
public class CustomGridAdapter extends BaseAdapter {
    private ArrayList<MovieItem> movieItems;
    Context context;

    public CustomGridAdapter(Context context, ArrayList<MovieItem> movieItems) {
        this.movieItems = movieItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public MovieItem getItem(int i) {
        return movieItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = inflater.inflate(R.layout.grid_cell, null);
            ImageView imageView = (ImageView)grid.findViewById(R.id.image);
            String url = "http://image.tmdb.org/t/p/w500/" + movieItems.get(i).imageurl;
            Glide.with(context)
                 .load(url)
                 .into(imageView);
        } else {
            grid = view;
        }
        return grid;

    }

    public void addItem() {
        movieItems.add(
                movieItems.size(),
                movieItems.get(0)
        );
    }

}
