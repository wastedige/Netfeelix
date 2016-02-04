package com.example.shacra.netfeelix;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by shacra on 2/3/2016.
 */
public class ViewHolderItem {
    public ImageView imageViewHolder;

    public ViewHolderItem(View v) {
        imageViewHolder =  (ImageView) v.findViewById(R.id.image);
        //v.setTag(this);
    }
}
