package com.example.shacra.netfeelix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        showMovieDetails(new String[] {
                intent.getStringExtra("url"),
                intent.getStringExtra("title"),
                intent.getStringExtra("overview")
        });

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
