package com.ideochallenge.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Maciej Szalek on 2019-10-18.
 */

@DatabaseTable(tableName = NearbyPlace.TAB_NEARBY)
public class NearbyPlace {

    static final String TAB_NEARBY = "tab_nearby";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_VICINITY = "vicinity";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LNG = "lng";

    public NearbyPlace(){}

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME)
    private String nearbyName;

    @DatabaseField(columnName = COLUMN_VICINITY)
    private String nearbyVicinity;

    @DatabaseField(columnName = COLUMN_LAT)
    private double nearbyLat;

    @DatabaseField(columnName = COLUMN_LNG)
    private double nearbyLng;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNearbyName() {
        return nearbyName;
    }

    public void setNearbyName(String nearbyName) {
        this.nearbyName = nearbyName;
    }

    public String getNearbyVicinity() {
        return nearbyVicinity;
    }

    public void setNearbyVicinity(String nearbyVicinity) {
        this.nearbyVicinity = nearbyVicinity;
    }

    public double getNearbyLat() {
        return nearbyLat;
    }

    public void setNearbyLat(double nearbyLat) {
        this.nearbyLat = nearbyLat;
    }

    public double getNearbyLng() {
        return nearbyLng;
    }

    public void setNearbyLng(double nearbyLng) {
        this.nearbyLng = nearbyLng;
    }
}
