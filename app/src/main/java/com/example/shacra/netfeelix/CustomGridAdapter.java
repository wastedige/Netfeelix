package com.example.shacra.netfeelix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * Created by shacra on 12/31/2015.
 */
public class CustomGridAdapter extends BaseAdapter {
    private final String[] image;
    Context context;

    public CustomGridAdapter(Context context, String[] image ) {
        this.context = context;
        this.image = image;
    }

    @Override
    public int getCount() {

        return image.length / 2;
    }

    @Override
    public Object getItem(int i) {
        return image[i * 2 + 1];
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

            Glide.with(context)
                 .load("http://image.tmdb.org/t/p/w500/" + image[i * 2 + 1])
                 .into(imageView);
        } else {
            grid = view;
        }
        return grid;

    }
}
