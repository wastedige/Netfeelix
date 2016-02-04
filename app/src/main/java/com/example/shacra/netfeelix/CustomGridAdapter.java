package com.example.shacra.netfeelix;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/**
 * Created by shacra on 12/31/2015.
 */

public class CustomGridAdapter extends BaseAdapter {
    private ArrayList<MovieItem> movieItems = new ArrayList<>();
    int placeholderHeight, placeholderWidth;
    Context context;

    public CustomGridAdapter(Context context, ArrayList<MovieItem> movieItems) {
        this.context = context;
        this.movieItems = movieItems;
        // calculate thumbnail size
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        placeholderWidth = size.x / 2 ;
        placeholderHeight = size.x * 213 / 154 / 2;
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
        ViewHolderItem viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_cell, null);
            // Set ViewHolder to be used on future calls

            viewHolder = new ViewHolderItem(grid);
            grid.setTag(viewHolder);
        } else {
            grid = view;
            viewHolder = (ViewHolderItem) view.getTag();
        }
        // ImageView imageView = (ImageView)grid.findViewById(R.id.image);
        //Log.v("Loaded into View ", i + ": " + movieItems.get(i).getTitle());
        String url = "http://image.tmdb.org/t/p/w342/" + movieItems.get(i).imageurl;
        Glide.with(context)
                .load(url)
                .override(placeholderWidth, placeholderHeight)
                .centerCrop()
                .placeholder(R.drawable.loading_big)
                .into(viewHolder.imageViewHolder);
        return grid;

    }

    public void updateItems(ArrayList<MovieItem> movieItems) {
        this.movieItems = movieItems;
        notifyDataSetChanged();
    }

}
