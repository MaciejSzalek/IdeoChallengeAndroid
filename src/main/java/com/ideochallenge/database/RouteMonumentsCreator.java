package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.models.Destination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-27.
 */

public class RouteMonumentsCreator {

    public static void createRouteMonuments(Context context) throws SQLException {

        DBHelper dbHelper = new DBHelper(context);
        List<Destination> destinationList = new ArrayList<>();


        //Monuments
        destinationList.add
                (new Destination("Monuments", "Pomnik Czynu Rewolucyjnego",
                        50.04056, 21.99948,
                        10, 2));
        destinationList.add
                (new Destination("Monuments", "Pomnik Urwis z procą",
                        50.03545, 22.003019,
                        10, 2));
        destinationList.add
                (new Destination("Monuments", "Pomnik Tadeusza Nalepy",
                        50.0372, 22.00185,
                        10, 2));
        destinationList.add
                (new Destination("Monuments", "Pomnik Sybiraków",
                        50.05009, 21.97694,
                        10, 2));

        for (Destination destination: destinationList){
            try {
                dbHelper.createOrUpdateDestination(destination);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
