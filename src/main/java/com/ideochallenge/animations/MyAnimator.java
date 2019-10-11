package com.ideochallenge.animations;

import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-11.
 */

public class MyAnimator implements Runnable {

    public MyAnimator(Marker marker, List<LatLng> markerList){
        this.trackingMarker = marker;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.markerList = markerList;

    }

    //speed in millis seconds
    private static final int ANIMATE_SPEED = 1500;
    private static final int ANIMATE_SPEED_TURN = 1000;
    private static final int BEARING_OFFSET = 20;

    private final Interpolator interpolator = new LinearInterpolator();
    long start = SystemClock.uptimeMillis();

    private Marker trackingMarker;
    private LatLng startPosition,  endPosition;
    private List<LatLng> markerList = new ArrayList<>();
    int currentIndex = 0;

    private Handler mHandler = new Handler();

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
