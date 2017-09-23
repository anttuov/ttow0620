package com.anttuov.a38_1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends Activity {

    private ImageView imageView;
    private TextView textView;
    private ProgressBar progressBar;
    private final String PATH = "https://i.imgur.com/";
    private String[] images = {"ahtoJWp.jpg","mJ0NvPe.jpg","qyJ1tjr.gif"};
    private int imageIndex;
    private DownloadImageTask task;
    private float x1,x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get views
        imageView = (ImageView)findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // start showing images
        imageIndex = 0;
        showImage();
    }

    // method gets called when user performs any touch event on screen
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                if (x1 < x2) {
                    imageIndex--;
                    if (imageIndex < 0) imageIndex = images.length-1;
                } else {
                    imageIndex++;
                    if (imageIndex > (images.length-1)) imageIndex = 0;
                }
                showImage();
                break;
        }
        return false;
    }

    public void showImage() {
        // create a new AsyncTask object
        task = new DownloadImageTask();
        // start asynctask
        task.execute(PATH + images[imageIndex]);
    }


    private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected void onPreExecute() {
            // show loading progress bar
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            URL imageUrl;
            Bitmap bitmap = null;
            try {
                imageUrl = new URL(urls[0]);
                InputStream in = imageUrl.openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("<<LOADIMAGE>>", e.getMessage());
            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            textView.setText("Image " + (imageIndex + 1) + "/" + images.length);
            // hide loading progress bar
            progressBar.setVisibility(View.INVISIBLE);

        }
    }


}