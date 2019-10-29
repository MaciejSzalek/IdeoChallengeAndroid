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
import com.ideochallenge.models.Route;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Maciej Szalek on 2019-10-14.
 */

public class BotCreator {

    private DBHelper dbHelper;
    private GoogleMap mMap;
    private Marker botMarker;
    private BotAnimator botAnimator;
    private Context context;
    private Route route;

    private List<Route> routeList = new ArrayList<>();
    private List<Destination> destinationList = new ArrayList<>();
    private List<LatLng> destinationLatLngList = new ArrayList<>();

    public BotCreator(Context context, GoogleMap map){
        this.context = context;
        this.mMap = map;
        //this.botMarker = botMarker;
    }

    public void createNewBot(){
        getAndUpdateRoute();
        getAndUpdateDestinationFromCategory();
        convertDestinationToLatLng();
        startBotAnimation();
    }

    private void getAndUpdateRoute(){
        dbHelper = new DBHelper(context);
        try {
            routeList.clear();
            routeList = dbHelper.getAllRoute();
            route = routeList.get(randomRouteIndex(routeList.size()));
            route.setPoints(route.getPoints() + randomPoints());
            route.setVisitors(route.getVisitors() + 1);
            dbHelper.createOrUpdateRoute(route);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getAndUpdateDestinationFromCategory() {
        dbHelper = new DBHelper(context);
        try {
            destinationList.clear();
            destinationList = dbHelper.getAllDestinationFromCategory(route.getCategory());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Destination destination: destinationList){
            long points = destination.getPoints() + randomPoints();
            long visitors = destination.getVisitors() + 1;
            destination.setPoints(points);
            destination.setVisitors(visitors);
            try {
                dbHelper.createOrUpdateDestination(destination);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void startBotAnimation(){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(destinationLatLngList.get(0));
        markerOptions.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        botMarker = mMap.addMarker(markerOptions);
        botAnimator = new BotAnimator(botMarker, destinationLatLngList);
        botAnimator.run();
    }

    private List<LatLng> convertDestinationToLatLng(){
        if(!destinationList.isEmpty()){
            destinationLatLngList.clear();
            for(Destination destination: destinationList){
                double lat = destination.getLat();
                double lng = destination.getLng();
                destinationLatLngList.add(new LatLng(lat, lng));
            }
        }
        return destinationLatLngList;
    }

    private int randomRouteIndex(int routeListSize){
        int min = 0;
        int max = routeListSize - 1;
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private long randomPoints(){
        int min = 2;
        int max = 5;
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

}
