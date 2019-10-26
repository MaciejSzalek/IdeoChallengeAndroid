package com.ideochallenge.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Maciej Szalek on 2019-10-07.
 */

@DatabaseTable(tableName = Destination.TAB_DESTINATION)
public class Destination {

    static final String TAB_DESTINATION = "tab_destination";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_POINTS = "points";
    private static final String COLUMN_VISITORS = "visitors";

    public Destination(){}

    public Destination(String category, String name, double lat, double lng,
                       long points, long visitors){
        this.name = name;
        this.category = category;
        this.lat = lat;
        this.lng = lng;
        this.points = points;
        this.visitors = visitors;
    }

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_CATEGORY)
    private String category;

    @DatabaseField(columnName = COLUMN_NAME)
    private String name;

    @DatabaseField(columnName = COLUMN_LATITUDE)
    private double lat;

    @DatabaseField(columnName = COLUMN_LONGITUDE)
    private double lng;

    @DatabaseField(columnName = COLUMN_POINTS)
    private long points;

    @DatabaseField(columnName = COLUMN_VISITORS)
    private long visitors;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public long getVisitors() {
        return visitors;
    }

    public void setVisitors(long visitors) {
        this.visitors = visitors;
    }

}
