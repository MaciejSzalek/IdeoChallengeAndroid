package com.ideochallenge.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Maciej Szalek on 2019-10-14.
 */

@DatabaseTable(tableName = Route.TAB_ROUTE)
public class Route {

    static final String TAB_ROUTE = "tab_route";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_POINTS = "points";
    private static final String COLUMN_VISITORS = "visitors_number";

    public Route(){}

    public Route(String category, long points, long visitors){
        this.category = category;
        this.points = points;
        this.visitors = visitors;
    }

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_CATEGORY)
    private String category;

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
