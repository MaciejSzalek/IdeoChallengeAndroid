package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.models.Route;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-20.
 */

public class RouteCreator {

    public static void createRoute(Context context) throws SQLException {

        DBHelper dbHelper = new DBHelper(context);
        List<Route> routeList = new ArrayList<>();

        routeList.add(new Route("Architecture", 10, 2));
        routeList.add(new Route("Culture and Art", 10, 2));
        routeList.add(new Route("Entertainment", 10, 2));
        routeList.add(new Route("Monuments", 10, 2));
        routeList.add(new Route("Recreation", 10, 2));
        routeList.add(new Route("Restaurants and clubs", 10, 2));
        routeList.add(new Route("Sport", 10, 2));

        for(Route route: routeList){
            dbHelper.createOrUpdateRoute(route);
        }
    }
}
