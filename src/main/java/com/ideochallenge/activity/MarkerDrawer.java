package com.ideochallenge.activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ideochallenge.R;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.models.Destination;
import com.ideochallenge.models.NearbyPlace;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-11-07.
 */

class MarkerDrawer {

    public MarkerDrawer(){}

    static Marker playerMarker(GoogleMap mMap, LatLng mOrigin){
        MarkerOptions markerOptions;
        markerOptions = new MarkerOptions();
        markerOptions.position(mOrigin);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.man_72));
        markerOptions.title("You");
        return mMap.addMarker(markerOptions);
    }

    static Marker targetMarker(GoogleMap mMap, LatLng mDest){
        MarkerOptions markerOptions;
        markerOptions = new MarkerOptions();
        markerOptions.position(mDest);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.target_48));
        return mMap.addMarker(markerOptions);
    }

    static ArrayList<Marker> destinationMarkerList(DBHelper dbHelper,
                                               GoogleMap mMap,
                                               String routeCategory){
        List<Destination> playerDestinationList = new ArrayList<>();
        ArrayList<Marker> destinationMarkerList = new ArrayList<>();
        MarkerOptions markerOptions = new MarkerOptions();
        try {
            if(!playerDestinationList.isEmpty()){
                playerDestinationList.clear();
            }
            playerDestinationList = dbHelper
                    .getAllDestinationFromCategory(routeCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Destination destination: playerDestinationList){
            LatLng latLng = new LatLng(destination.getLat(), destination.getLng());
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_24));
            markerOptions.title(destination.getCategory());
            markerOptions.snippet(destination.getName());
            Marker destinationMarker = mMap.addMarker(markerOptions);
            destinationMarkerList.add(destinationMarker);
        }
        return destinationMarkerList;
    }

    static List<NearbyPlace> nearbyMarkerList(DBHelper dbHelper, GoogleMap mMap){
        List<NearbyPlace> nearbyDatabaseList = new ArrayList<>();
        try {
            nearbyDatabaseList = dbHelper.getAllNearbyPlace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(NearbyPlace nearbyPlace: nearbyDatabaseList){
            LatLng latLng = new LatLng(nearbyPlace.getNearbyLat(),
                    nearbyPlace.getNearbyLng());
            MarkerOptions markerOptions;
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.nearby_24));
            markerOptions.title(nearbyPlace.getNearbyName());
            mMap.addMarker(markerOptions);
        }
        return nearbyDatabaseList;
    }
}
