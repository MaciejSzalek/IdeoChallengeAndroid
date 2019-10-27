package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.models.Destination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-27.
 */

public class RouteEntertainmentCreator {

    public static void createRouteEntertainment(Context context) throws SQLException {

        DBHelper dbHelper = new DBHelper(context);
        List<Destination> destinationList = new ArrayList<>();

        //Entertainment
        destinationList.add
                (new Destination("Entertainment", "Muzeum Dobranocek",
                        50.03693, 22.00337,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Park Linowy  Expedycja",
                        50.032153, 22.006094,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Tor ICF Karting",
                        50.0481, 21.9919,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Reskart Racing",
                        50.035172, 21.99181,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Park Trampolin Happy Jump",
                        50.054218, 21.991179,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Park Linowy Linoskoczek",
                        49.98984, 22.0295,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Studio Pole Dance",
                        50.05327, 22.01518,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Centrum Zabawy WyWrotki",
                        50.03644, 22.00697,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "ProGame Paintball",
                        50.04655, 21.96017,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Skatepark",
                        50.03087, 22.00464,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Science and Technology Park",
                        50.074102, 21.963003,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Laserowa Twierdza - Laserowy Paintball",
                        50.03895, 22.01144,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Przystań na Lato",
                        50.03136, 22.00541,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "ExitMania Escape Room",
                        50.03461, 21.99455,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Podpromie",
                        50.0294, 22.0013,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Floating",
                        50.05075, 22.01201,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Flyboard",
                        50.008479, 21.99038,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Ultimate Frisbee Rzeszów",
                        47.517201, 20.390625,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Mini GOLF",
                        50.009652, 21.992371,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "WydostanSie - Escape Rooms",
                        50.03904, 22.00821,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Gamekeeper",
                        50.027479, 22.018018,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Kula Bowling & Club",
                        50.049121, 21.975876,
                        10, 2));
        destinationList.add
                (new Destination("Entertainment", "Papugarnia Rzeszów",
                        50.049121, 21.975876,
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
