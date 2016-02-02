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
    private ArrayList<MovieItem> movieItems = new ArrayList<>();
    Context context;

    public CustomGridAdapter(Context context, ArrayList<MovieItem> movieItems) {
        this.context = context;
        this.movieItems = movieItems;
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


        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_cell, null);
        } else {
            grid = view;
        }
        ImageView imageView = (ImageView)grid.findViewById(R.id.image);
        //Log.v("Loaded into View ", i + ": " + movieItems.get(i).getTitle());
        String url = "http://image.tmdb.org/t/p/w500/" + movieItems.get(i).imageurl;
        Glide.with(context)
                .load(url)
                .into(imageView);
        return grid;

    }

    public void updateItems(ArrayList<MovieItem> movieItems) {
        this.movieItems = movieItems;
        notifyDataSetChanged();
    }

}
