package com.ideochallenge.animations;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.ideochallenge.bot.BotCounter;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.models.NearbyPlace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-11.
 */

public class BotAnimator implements Runnable {

    public BotAnimator(Marker marker, List<LatLng> destinationLatLngList){
        this.trackingMarker = marker;
        this.destinationLatLngList = destinationLatLngList;
    }

    private Marker trackingMarker;
    private Handler mHandler = new Handler();
    private final Interpolator interpolator = new LinearInterpolator();

    private List<LatLng> destinationLatLngList = new ArrayList<>();

    private static final int ANIMATE_SPEED = 5000;
    private long start = SystemClock.uptimeMillis();
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
            if(currentIndex < destinationLatLngList.size()-2){
                currentIndex++;
                start = SystemClock.uptimeMillis();
                mHandler.postDelayed(this, 16);

            } else {
                currentIndex++;
                trackingMarker.remove();
                BotCounter.subtractBot();
            }
        }
    }

    private LatLng getEndLatLng() {
        return destinationLatLngList.get(currentIndex + 1);
    }

    private LatLng getBeginLatLng() {
        return destinationLatLngList.get(currentIndex);
    }
}
