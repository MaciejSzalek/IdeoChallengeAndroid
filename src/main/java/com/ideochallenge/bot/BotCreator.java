package com.ideochallenge.bot;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ideochallenge.animations.BotAnimator;
import com.ideochallenge.database.DBHelper;
import com.ideochallenge.models.Destination;
import com.ideochallenge.models.NearbyPlace;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Maciej Szalek on 2019-10-14.
 */

public class BotCreator {

    private GoogleMap mMap;
    private Marker marker;
    private List<LatLng> routeList = new ArrayList<>();
    private List<NearbyPlace> nearbyPlaces = new ArrayList<>();
    private BotAnimator botAnimator;
    private DBHelper dbHelper;
    private Context context;

    private enum Routes {
        history,
        food,
        hipster,
        fun
    }

    public BotCreator(){}

    public BotCreator(Context context, GoogleMap map){
        this.context = context;
        this.mMap = map;
    }
    private void randomRout() {
        int num = 0;
    }

    public static long randomPoints(){
        int min = 1;
        int max = 5;
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public void createNewBot(){
        try {
            getRouteList();
            getNearbyPlaces();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(routeList.get(0));
        markerOptions.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        marker = mMap.addMarker(markerOptions);
        botAnimator = new BotAnimator(marker, routeList, nearbyPlaces);
        botAnimator.run();
    }

    private void updateDestinationDatails(){

    }

    private List<LatLng> getRouteList() throws SQLException {
        dbHelper = new DBHelper(context);
        List<Destination> destinations;
        destinations = dbHelper.getAllDestination();
        for(Destination destination: destinations){
            routeList.add(new LatLng(destination.getLat(), destination.getLng()));
        }
        return routeList;
    }

    private List<NearbyPlace> getNearbyPlaces() throws SQLException{
        dbHelper = new DBHelper(context);
        nearbyPlaces = dbHelper.getAllNearbyPlace();
        return nearbyPlaces;
    }
}
