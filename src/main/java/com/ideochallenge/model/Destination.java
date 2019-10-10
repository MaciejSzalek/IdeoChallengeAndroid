package com.ideochallenge.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Maciej Szalek on 2019-10-07.
 */

@DatabaseTable(tableName = Destination.TAB_DESTINATION)
public class Destination {

    public static final String TAB_DESTINATION = "tab_destination";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_CATEGORY = "category";

    public Destination(){}

    public Destination(String name, String category, double lat, double lng){
        this.name = name;
        this.category = category;
        this.lat = lat;
        this.lng = lng;
    }

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME)
    private String name;

    @DatabaseField(columnName = COLUMN_CATEGORY)
    private String category;

    @DatabaseField(columnName = COLUMN_LATITUDE)
    private double lat;

    @DatabaseField(columnName = COLUMN_LONGITUDE)
    private double lng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
