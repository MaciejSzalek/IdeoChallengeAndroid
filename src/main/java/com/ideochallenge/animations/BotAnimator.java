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
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.models.NearbyPlace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-11.
 */

public class BotAnimator implements Runnable {

    public BotAnimator(Marker marker,
                       List<LatLng> markerList,
                       List<NearbyPlace> nearbyPlaces){
        this.trackingMarker = marker;
        this.markerList = markerList;
        this.nearbyPlaces = nearbyPlaces;
    }

    private Marker trackingMarker;
    private Location trackingLocation = new Location("marker_location");
    private Location nearbyLocation = new Location("nearby_location");
    private Handler mHandler = new Handler();
    private final Interpolator interpolator = new LinearInterpolator();

    private List<LatLng> markerList = new ArrayList<>();
    private List<NearbyPlace> nearbyPlaces = new ArrayList<>();

    private static final int ANIMATE_SPEED = 3000;
    private long start = SystemClock.uptimeMillis();
    private int currentIndex = 0;
    private float distance;

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
        trackingLocation.setLatitude(newPosition.latitude);
        trackingLocation.setLongitude(newPosition.longitude);

        for(int i=0; i<nearbyPlaces.size(); i++){
            nearbyLocation.setLatitude(nearbyPlaces.get(i).getNearbyLat());
            nearbyLocation.setLongitude(nearbyPlaces.get(i).getNearbyLng());
            distance = trackingLocation.distanceTo(nearbyLocation);
            if(distance < 50) {
                trackingMarker.setTitle(nearbyPlaces.get(i).getNearbyName());
                trackingMarker.showInfoWindow();
                break;
            }else{
                trackingMarker.hideInfoWindow();
            }
        }

        if(t < 1.0){
            mHandler.postDelayed(this, 16);
        } else {
            if(currentIndex < markerList.size()-2){
                currentIndex++;
                start = SystemClock.uptimeMillis();
                mHandler.postDelayed(this, 16);

            } else {
                currentIndex++;
                trackingMarker.remove();
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
