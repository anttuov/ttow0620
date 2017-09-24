package com.anttuov.a38_3;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.anttuov.a38_3.R.id.imageView;


public class MainActivity extends AppCompatActivity {
    private ImageView gifImageView;
    private DownloadImageTask task;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

    }




    public void newGif(View view) {
        //JSONObject  jsonRootObject = new JSONObject(strJson);
        task = new DownloadImageTask();
        EditText editText = (EditText)findViewById(R.id.editText);
        String keyword = editText.getText().toString();
        String url = "http://api.giphy.com/v1/gifs/random?api_key=dc6zaTOxFJmzC&tag="+keyword;
        task.execute(url);


        }


    private class DownloadImageTask extends AsyncTask<String,String,String> {

        // this is done in UI thread, nothing this time
        @Override
        protected void onPreExecute() {
            int a = 1;
            // show loading progress bar
            //progressBar.setVisibility(View.VISIBLE);
        }

        // this is background thread, load image and pass it to onPostExecute
        @Override
        protected String doInBackground(String... url) {
            getUrlString test = new getUrlString();
            String response = "";
            try{
                response = test.run(url[0]);
            }
            catch (IOException e){}
            String imageurl = "";
            try {
                JSONObject obj = new JSONObject(response);
                imageurl = obj.getJSONObject("data").getString("image_original_url");
            }
            catch (JSONException e) {}
            return imageurl;
        }

        // this is done in UI thread
        @Override
        protected void onPostExecute(String url) {
            gifImageView = (ImageView) findViewById(imageView);
            Glide.with(ctx)
                    .load(url)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_cloud_download_black_24dp)
                    )
                    .transition(GenericTransitionOptions.with(R.anim.zoom_in))
                    .into(gifImageView);
            Toast.makeText(getBaseContext(), url, Toast.LENGTH_LONG).show();
        }
    }

    }



