package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.models.Route;

import java.sql.SQLException;

/**
 * Created by Maciej Szalek on 2019-10-20.
 */

public class RouteCreator {

    public static void createRoute(Context context) throws SQLException {

        DBHelper dbHelper = new DBHelper(context);

        Route route1 = new Route("history", 10, 2);

        dbHelper.createNewRoute(route1);
    }
}
