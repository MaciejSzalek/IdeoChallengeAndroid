package com.ideochallenge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.ideochallenge.animations.LatLngInterpolator;
import com.ideochallenge.animations.MarkerAnimator;
import com.ideochallenge.animations.MyAnimator;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.database.HistoryTrack;
import com.ideochallenge.directionhelpers.TaskLoadedCallback;
import com.ideochallenge.model.Destination;

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

    private MyAnimator myAnimator;

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

                /*new FetchURL(MainActivity.this)
                        .execute(getUrl(mOrigin, mDest, "walking"), "walking");*/

                //MarkerAnimator.startAnimation(mMarker, markerList,new LatLngInterpolator.Spherical());

                myAnimator = new MyAnimator(mMarker, markerList);
                myAnimator.run();

            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mOrigin = new LatLng(historyTrackList.get(0).getLat(), historyTrackList.get(0).getLng());
        mDest = new LatLng(historyTrackList.get(2).getLat(), historyTrackList.get(2).getLng());
        mMap = googleMap;


        MarkerOptions markerOptions;
        markerOptions = new MarkerOptions();
        markerOptions.position(mOrigin);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMarker = mMap.addMarker(markerOptions);

        for(Destination destination: historyTrackList){
            LatLng latLng = new LatLng(destination.getLat(), destination.getLng());
            //markerOptions = new MarkerOptions();
            //markerOptions.position(latLng);
            //mMap.addMarker(markerOptions);
            markerList.add(latLng);
        }

    }

    @Override
    public void onTaskDone(Object... values) {
        markerList = (ArrayList<LatLng>) values[0];
    }

    /*@Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }*/

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";

        return "https://maps.googleapis.com/maps/api/directions/" + output
                + "?" + parameters + "&key=" + getString(R.string.API_KEY);
    }

    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }

}
