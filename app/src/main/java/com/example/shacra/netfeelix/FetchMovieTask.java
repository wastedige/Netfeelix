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

/**
 * Created by shacra on 1/3/2016.
 */
public class FetchMovieTask extends AsyncTask<Void, Void, String[]> {
    GridView grid;
    Context targetContext;
    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    public FetchMovieTask(Context targetContext, GridView grid) {
        this.grid = grid;
        this.targetContext = targetContext;
    }

    @Override
    protected String[] doInBackground(Void... params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL =
                    "http://api.themoviedb.org/3/discover/movie?";
            final String SORT = "sort_by";
            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(SORT, "popularity.desc")
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
            return getPopularMoviesFromJson(forecastJsonStr);

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result != null)
            grid.setAdapter(new CustomGridAdapter(targetContext, result));
    }

    private String[] getPopularMoviesFromJson(String forecastJsonStr)
            throws JSONException {


        JSONObject movieJson = new JSONObject(forecastJsonStr);
        JSONArray resultsArray = movieJson.getJSONArray("results");

        // OWM returns daily forecasts based upon the local time of the city that is being
        // asked for, which means that we need to know the GMT offset to translate this data
        // properly.

        String[] resultStrs = new String[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {


            JSONObject singleMovie = resultsArray.getJSONObject(i);
            String imageAddress = singleMovie.getString("poster_path");

            resultStrs[i] = imageAddress;
        }

        for (String s : resultStrs) {
            Log.v("ResultString", "entry: " + s);
        }
        return resultStrs;

    }
}