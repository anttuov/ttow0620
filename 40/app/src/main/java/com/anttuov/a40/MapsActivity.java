package com.anttuov.a40;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DownloadJSON task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }


            @Override
            public View getInfoContents(Marker arg0) {

                View v = getLayoutInflater().inflate(R.layout.info, null);
                TextView infotext = (TextView) v.findViewById(R.id.infotext);
                infotext.setText(arg0.getSnippet());

                return v;

            }
        });


        task = new DownloadJSON();
        String url = "http://ptm.fi/materials/golfcourses/golf_courses.json";
        task.execute(url);
    }



    private class DownloadJSON extends AsyncTask<String,Void,JSONObject> {


        @Override
        protected void onPreExecute() {
            int a = 1;
        }


        @Override
        protected JSONObject doInBackground(String... url) {
            getUrlString test = new getUrlString();
            String response = "";
            try{
                response = test.run(url[0]);
            }
            catch (IOException e){}
            JSONObject obj = null;
            try {
                obj = new JSONObject(response);
            }
            catch (JSONException e) {}
            return obj;
        }


        @Override
        protected void onPostExecute(JSONObject json) {

            try {
                String course;
                String address;
                String phone;
                String email;
                Double lat;
                Double lng;
                String web;
                String type;
                float color;
                LatLng pos = null;
                JSONArray courses = json.getJSONArray("courses");
                for (int i = 0; i < courses.length(); i++) {
                    JSONObject row = courses.getJSONObject(i);
                    course = row.getString("course");
                    lat = row.getDouble("lat");
                    lng = row.getDouble("lng");
                    address = row.getString("address");
                    type = row.getString("type");
                    phone = row.getString("phone");
                    email = row.getString("email");
                    web = row.getString("web");
                    pos = new LatLng(lat, lng);

                    if (type.equals("Etu")) {
                        color = BitmapDescriptorFactory.HUE_BLUE;
                    } else if (type.equals("Kulta/Etu")) {
                        color = BitmapDescriptorFactory.HUE_GREEN;
                    }
                    else {
                        color = BitmapDescriptorFactory.HUE_YELLOW;
                    }


                    mMap.addMarker(new MarkerOptions()
                            .position(pos)
                            .title(course)
                            .snippet(course+"\n"+address+"\n"+phone+"\n"+email+"\n"+web)
                            .icon(BitmapDescriptorFactory.defaultMarker(color))
                    );

                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
            }
            catch (JSONException e) {}

            Toast.makeText(getBaseContext(), "jee", Toast.LENGTH_LONG).show();
        }
    }






}
