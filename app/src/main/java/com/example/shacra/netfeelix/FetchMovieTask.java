package com.example.shacra.netfeelix;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shacra on 1/3/2016.
 */
public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<MovieItem>> {
    GridView grid;
    Context targetContext;
    String forecastJsonStr;
    CallbackInterface callback;
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    public FetchMovieTask(Context targetContext, GridView grid, CallbackInterface callback) {
        this.grid = grid;
        this.targetContext = targetContext;
        this.callback = callback;
    }


    @Override
    protected ArrayList<MovieItem> doInBackground(String... params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;



        // Will contain the raw JSON response as a string.
        forecastJsonStr = null;

        try {

            // http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get
            final String FORECAST_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?" + params[0];
//            final String SORT = "sort_by";
            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
//                    .appendQueryParameter(SORT, "popularity.desc")
                    .appendQueryParameter(APPID_PARAM, "06a222d2879f3102f48bcb46e629de99")
                    .build();

            URL url = new URL(builtUri.toString());

            Log.v(LOG_TAG, "Built URI " + builtUri.toString());

            // Create the request to cloud, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();


        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }


        try {
            return getMovieThumbnailsFromJson(forecastJsonStr);

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieItem> result) {
        if (result != null)
            callback.onAsyncComplete(result);
//        if (result != null)
//            adapter.AddNewItemsAndRefresh(result);
    }


    private ArrayList<MovieItem> getMovieThumbnailsFromJson(String movieJsonStr)
            throws JSONException {

        ArrayList<MovieItem> tempMovieItemList = new ArrayList<>();

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray resultsArray = movieJson.getJSONArray("results");

        for (int i = 0; i < resultsArray.length(); i++) {


            JSONObject singleMovie = resultsArray.getJSONObject(i);
            MovieItem temp_movie_item = new MovieItem(
                    singleMovie.getString("id"),
                    singleMovie.getString("poster_path"),
                    singleMovie.getString("title"),
                    singleMovie.getString("overview")
            );
            tempMovieItemList.add(i, temp_movie_item);
            //Log.v("Movies fetched", i + " " + temp_movie_item.getTitle());
        }

        return tempMovieItemList;

    }
}
