package com.example.shacra.netfeelix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends Activity {

    String jsonString;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        jsonString = intent.getStringExtra("movieJson");
        position = intent.getIntExtra("position", -1);

        try {
            parseMovieDetailsFromJson(jsonString, position);
        } catch (JSONException e) {
            Log.e("DetailActivity Error", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private String[] parseMovieDetailsFromJson(String movieJsonStr, int position)
            throws JSONException {
        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray resultsArray = movieJson.getJSONArray("results");
        // single out the JSON string for the selected movie
        JSONObject singleMovie = resultsArray.getJSONObject(position);

        String imageAddress = singleMovie.getString("poster_path");
        String overview = singleMovie.getString("overview");

        Log.v("LOG", overview + "");




        return null;
    }
}
