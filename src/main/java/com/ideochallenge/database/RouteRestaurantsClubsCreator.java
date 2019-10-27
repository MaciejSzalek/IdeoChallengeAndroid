package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.models.Destination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-27.
 */

public class RouteRestaurantsClubsCreator {

    public static void createRouteRestaurantsClubs(Context context) throws SQLException {

        DBHelper dbHelper = new DBHelper(context);
        List<Destination> destinationList = new ArrayList<>();

        //Restaurants and clubs\
        destinationList.add
                (new Destination("Restaurants and clubs", "Parole Art Restaurant",
                        50.03906, 21.99974,
                        10, 2));
        destinationList.add
                (new Destination("Restaurants and clubs", "Restauracja Oranżeria",
                        50.041647, 21.998543,
                        10, 2));
        destinationList.add
                (new Destination("Restaurants and clubs", "Kawiarnia PRZYSTAŃ",
                        50.008479, 21.99038,
                        10, 2));
        destinationList.add
                (new Destination("Restaurants and clubs", "Jazz Room",
                        50.03742, 22.00255,
                        10, 2));
        destinationList.add
                (new Destination("Restaurants and clubs", "Lody  u Myszki",
                        50.03467, 22.0008,
                        10, 2));
        destinationList.add
                (new Destination("Restaurants and clubs", "Lukr",
                        50.027014, 22.016533,
                        10, 2));
        destinationList.add
                (new Destination("Restaurants and clubs", "Pewex",
                        50.03769, 22.00542,
                        10, 2));
        destinationList.add
                (new Destination("Restaurants and clubs", "Grand Club",
                        50.03754, 22.00253,
                        10, 2));
        destinationList.add
                (new Destination("Restaurants and clubs", "Vinyl",
                        50.040145, 22.007355,
                        10, 2));
        destinationList.add
                (new Destination("Restaurants and clubs", "Capella Restaurant @ Lobby Lounge",
                        50.040145, 22.007355,
                        10, 2));
        destinationList.add
                (new Destination("Restaurants and clubs", "Wina Dajcie",
                        50.03668, 22.00144,
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
