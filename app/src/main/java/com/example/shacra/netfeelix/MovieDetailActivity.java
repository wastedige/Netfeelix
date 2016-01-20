package com.example.shacra.netfeelix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends Activity {

    String jsonString;
    int position;
    ImageView detailed_image, rating_image;
    TextView detailed_text_title;
    TextView detailed_text_overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        detailed_image = (ImageView) findViewById(R.id.detailed_image);
        rating_image = (ImageView) findViewById(R.id.rating_image);
        detailed_text_title = (TextView) findViewById(R.id.detailed_text_title);
        detailed_text_overview = (TextView) findViewById(R.id.detailed_text_overview);

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

    private void parseMovieDetailsFromJson(String movieJsonStr, int position)
            throws JSONException {
        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray resultsArray = movieJson.getJSONArray("results");
        // single out the JSON string for the selected movie
        JSONObject singleMovie = resultsArray.getJSONObject(position);


        Log.v("LOG", singleMovie + "");
        showMovieDetails(
                singleMovie.getString("poster_path"),
                singleMovie.getString("original_title"),
                singleMovie.getString("overview")

        );

        return;
    }

    private void showMovieDetails(String... params ) {
        final String image_address = "http://image.tmdb.org/t/p/w780/" + params[0];
        Glide.with(this)
                .load(image_address)
                .into(detailed_image);

        detailed_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetWallpaper wallpaper_setter = new SetWallpaper(getApplicationContext());
                wallpaper_setter.execute( image_address );
            }
        });

        detailed_text_title.setText(params[1]);
        detailed_text_overview.setText(params[2]);



    }
}
