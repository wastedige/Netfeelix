package com.example.shacra.netfeelix;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;

/**
 * Created by shacra on 2/4/2016.
 */
public class App extends Application {
    @Override public void onCreate() {
        super.onCreate();
        ViewTarget.setTagId(R.id.glide_tag);
    }
}
