package com.ideochallenge.animations;

import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-11.
 */

public class MyAnimator implements Runnable {

    public MyAnimator(GoogleMap mMap, Marker marker, List<LatLng> markerList){
        this.trackingMarker = marker;
        this.markerList = markerList;
        this.mMap = mMap;
    }
    //speed in millis seconds
    private static final int ANIMATE_SPEED = 1500;

    private Handler mHandler = new Handler();
    private final Interpolator interpolator = new LinearInterpolator();
    private long start = SystemClock.uptimeMillis();
    private GoogleMap mMap;

    private Marker trackingMarker;
    private List<LatLng> markerList = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    public void run() {
        long elapsed = SystemClock.uptimeMillis() - start;
        double t = interpolator.getInterpolation((float) elapsed / ANIMATE_SPEED);

        LatLng beginLatLng = getBeginLatLng();
        LatLng endLatLng = getEndLatLng();

        double lat = t * endLatLng.latitude + (1-t) * beginLatLng.latitude;
        double lng = t * endLatLng.longitude + (1-t) * beginLatLng.longitude;
        LatLng newPosition = new LatLng(lat, lng);

        trackingMarker.setPosition(newPosition);

        if(t < 1.0){
            mHandler.postDelayed(this, 16);
        } else {
            if(currentIndex < markerList.size()-2){
                currentIndex++;
                start = SystemClock.uptimeMillis();
                mHandler.postDelayed(this, 16);

            } else {
                currentIndex++;
            }
        }
    }

    private LatLng getEndLatLng() {
        return markerList.get(currentIndex + 1);
    }

    private LatLng getBeginLatLng() {
        return markerList.get(currentIndex);
    }
}
