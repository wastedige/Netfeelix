package com.example.shacra.netfeelix;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Schann on 1/19/2016.
 */
public class SetWallpaper extends AsyncTask <String, Void, Void> {
    Context main_activity_instance;

    public SetWallpaper(Context a) {
        main_activity_instance = a;
    }

    @Override
    protected Void doInBackground(String... params) {
        // http://www.teusink.eu/2014/11/load-bitmap-efficiently-and-set-it-as.html
        WallpaperManager wpm = WallpaperManager.getInstance(main_activity_instance);
        try {
            InputStream input = new URL (params[0]).openStream();
            Bitmap temp_bitmap = BitmapFactory.decodeStream(input);
            // -----------
            Paint p = new Paint();
            p.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)); // gray tint paint
            Bitmap mutableBitmap = temp_bitmap.copy(Bitmap.Config.ARGB_8888, true); // create a mutable bitmap instance of the image
            Canvas canvas = new Canvas(mutableBitmap); // create canvas to apply paint on bitmap
            canvas.drawBitmap(mutableBitmap, 0, 0, p); // apply
            wpm.setBitmap(mutableBitmap);
        } catch (MalformedURLException e) {
            Log.v("MalformedURLException", e.toString());
        } catch (IOException e) {
            Log.v("IOException", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // Toast.makeText(main_activity_instance, "Wallpaper set!", Toast.LENGTH_SHORT);
    }
}

