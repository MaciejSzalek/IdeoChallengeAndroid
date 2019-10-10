package com.ideochallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.database.HistoryTrack;
import com.ideochallenge.directionhelpers.FetchURL;
import com.ideochallenge.directionhelpers.TaskLoadedCallback;
import com.ideochallenge.model.Destination;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
        , TaskLoadedCallback {

    private DBHelper dbHelper;
    private GoogleMap mMap;
    private Marker mMarker;
    private List<Destination> historyTrackList = new ArrayList<>();
    private Polyline currentPolyline;
    private List<LatLng> markerList = new ArrayList<>();

    private LatLng mOrigin;
    private LatLng mDest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        Button button = findViewById(R.id.test_btn);
        final TextView txtView = findViewById(R.id.test_txt);

        dbHelper = new DBHelper(this);


        try {
            historyTrackList = dbHelper.getAllDestination();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(historyTrackList.isEmpty()){
            try {
                HistoryTrack.createHistoryTrack(this);
                historyTrackList = dbHelper.getAllDestination();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new FetchURL(MainActivity.this)
                            .execute(getUrl(mOrigin, mDest, "walking"), "walking");
                } catch (NullPointerException e){
                    Toast.makeText(MainActivity.this, "Brak neta", Toast.LENGTH_SHORT).show();
                    Log.d("EX", e.toString());
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mOrigin = new LatLng(historyTrackList.get(0).getLat(), historyTrackList.get(0).getLng());
        mDest = new LatLng(historyTrackList.get(2).getLat(), historyTrackList.get(2).getLng());

        mMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mOrigin);
        mMarker = mMap.addMarker(markerOptions);

        markerOptions = new MarkerOptions();
        markerOptions.position(mDest);
        mMarker = mMap.addMarker(markerOptions);
    }

    @Override
    public void onTaskDone(Object... values) {
        markerList = (ArrayList<LatLng>) values[0];
        for(LatLng latLng: markerList){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            mMarker = mMap.addMarker(markerOptions);
        }

    }

    /*@Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }*/

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output
                + "?" + parameters + "&key=" + getString(R.string.API_KEY);
        return url;
    }

}
