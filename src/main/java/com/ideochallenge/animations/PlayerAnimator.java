package com.ideochallenge.animations;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-11.
 */

public class PlayerAnimator implements Runnable {

    public PlayerAnimator(GoogleMap mMap, Marker marker, List<LatLng> markerList){
        this.trackingMarker = marker;
        this.markerList = markerList;
        this.mMap = mMap;
    }
    //speed in millis seconds
    private static final int ANIMATE_SPEED = 4000;
    private static final int ANIMATE_SPEED_TURN = 200;
    private static final int BEARING_OFFSET = 5;
    private static final float TILT = 90;

    private Handler mHandler = new Handler();
    private final Interpolator interpolator = new LinearInterpolator();
    private long start = SystemClock.uptimeMillis();
    private GoogleMap mMap;

    private Marker trackingMarker;
    private List<LatLng> markerList = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    public void run() {
        LatLng beginLatLng = getBeginLatLng();
        LatLng endLatLng = getEndLatLng();
        long elapsed = SystemClock.uptimeMillis() - start;
        double t = interpolator.getInterpolation((float) elapsed / ANIMATE_SPEED);
        double lat = t * endLatLng.latitude + (1-t) * beginLatLng.latitude;
        double lng = t * endLatLng.longitude + (1-t) * beginLatLng.longitude;


        setCameraPositionMovement(beginLatLng, endLatLng);
        LatLng newPosition = new LatLng(lat, lng);
        trackingMarker.setPosition(newPosition);

        if(t < 1.0){
            mHandler.postDelayed(this, 16);
        } else {
            if(currentIndex < markerList.size()-2){
                currentIndex++;
                LatLng begin = getBeginLatLng();
                LatLng end = getEndLatLng();
                setCameraPositionMovement(begin, end);

                start = SystemClock.uptimeMillis();
                mHandler.postDelayed(this, 16);

            } else {
                currentIndex++;
                setCameraPositionStop(trackingMarker.getPosition());
            }
        }
    }
    private void setCameraPositionStop(LatLng end) {

        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(end)
                        .bearing(0)
                        .tilt(0)
                        .zoom(16)
                        .build();

        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition),
                ANIMATE_SPEED_TURN,
                null
        );
    }
    private void setCameraPositionMovement(LatLng begin, LatLng end){
        float bearingL = bearingBetweenLatLng(begin, end);

        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(end)
                        .bearing(bearingL  + BEARING_OFFSET)
                        .tilt(TILT)
                        .zoom(mMap.getCameraPosition().zoom >=17 ? mMap.getCameraPosition().zoom : 17)
                        .build();

        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition),
                ANIMATE_SPEED_TURN,
                null
        );
    }

    private LatLng getEndLatLng() {
        return markerList.get(currentIndex + 1);
    }

    private LatLng getBeginLatLng() {
        return markerList.get(currentIndex);
    }

    private Location convertLatLngToLocation(LatLng latLng) {
        Location loc = new Location("someLoc");
        loc.setLatitude(latLng.latitude);
        loc.setLongitude(latLng.longitude);
        return loc;
    }

    private float bearingBetweenLatLng(LatLng begin,LatLng end) {
        Location beginL= convertLatLngToLocation(begin);
        Location endL= convertLatLngToLocation(end);

        return beginL.bearingTo(endL);
    }
}
