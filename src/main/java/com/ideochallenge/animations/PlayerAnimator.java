package com.ideochallenge.animations;

import android.graphics.Color;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ideochallenge.models.NearbyPlace;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-11.
 */

public class PlayerAnimator implements Runnable {

    public PlayerAnimator(GoogleMap mMap, Marker marker,
                          List<LatLng> markerList,
                          List<NearbyPlace> nearbyPlaces){
        this.mMap = mMap;
        this.trackingMarker = marker;
        this.markerList = markerList;
        this.nearbyPlaces = nearbyPlaces;
    }

    private static final int ANIMATE_SPEED = 500;
    private static final int ANIMATE_SPEED_TURN = 100;
    private static final int BEARING_OFFSET = 5;
    private static final float TILT = 90;

    private GoogleMap mMap;
    private Marker trackingMarker;
    private Polyline polyline;
    private PolylineOptions polylineOptions;
    private Handler mHandler = new Handler();
    private final Interpolator interpolator = new LinearInterpolator();

    private Location trackingLocation = new Location("marker_location");
    private Location nearbyLocation = new Location("nearby_location");
    private List<LatLng> markerList = new ArrayList<>();
    private List<NearbyPlace> nearbyPlaces = new ArrayList<>();

    private long start = SystemClock.uptimeMillis();
    private int currentIndex = 0;
    private float distance;
    private boolean showPolyline = false;


    @Override
    public void run() {
        LatLng beginLatLng = getBeginLatLng();
        LatLng endLatLng = getEndLatLng();
        long elapsed = SystemClock.uptimeMillis() - start;
        double t = interpolator.getInterpolation((float) elapsed / ANIMATE_SPEED);
        double lat = t * endLatLng.latitude + (1-t) * beginLatLng.latitude;
        double lng = t * endLatLng.longitude + (1-t) * beginLatLng.longitude;

        if(polyline == null) {
            polyline = initializePolyline();
        }
        setCameraPositionMovement(beginLatLng, endLatLng);
        LatLng newPosition = new LatLng(lat, lng);
        trackingMarker.setPosition(newPosition);
        updatePolyline(trackingMarker.getPosition());

        trackingLocation.setLatitude(newPosition.latitude);
        trackingLocation.setLongitude(newPosition.longitude);

        for(int i=0; i<nearbyPlaces.size(); i++){
            nearbyLocation.setLatitude(nearbyPlaces.get(i).getNearbyLat());
            nearbyLocation.setLongitude(nearbyPlaces.get(i).getNearbyLng());
            distance = trackingLocation.distanceTo(nearbyLocation);
            if(distance < 30) {
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
                        .zoom(15)
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
                        .zoom(mMap.getCameraPosition()
                                .zoom >=16 ? mMap.getCameraPosition().zoom : 16)
                        .build();

        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition),
                ANIMATE_SPEED_TURN,
                null
        );
    }

    private Polyline initializePolyline(){
        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE);
        polylineOptions.width(3);
        polylineOptions.add(new LatLng(markerList.get(0).latitude, markerList.get(0).longitude));
        return mMap.addPolyline(polylineOptions);
    }

    private void updatePolyline(LatLng latLng){
        List<LatLng> points = polyline.getPoints();
        points.add(latLng);
        polyline.setPoints(points);
    }

    private LatLng getEndLatLng(){
        return markerList.get(currentIndex + 1);
    }

    private LatLng getBeginLatLng(){
        return markerList.get(currentIndex);
    }

    private Location convertLatLngToLocation(LatLng latLng) {
        Location loc = new Location("someLoc");
        loc.setLatitude(latLng.latitude);
        loc.setLongitude(latLng.longitude);
        return loc;
    }

    private float bearingBetweenLatLng(LatLng begin,LatLng end){
        Location beginL= convertLatLngToLocation(begin);
        Location endL= convertLatLngToLocation(end);

        return beginL.bearingTo(endL);
    }
}
