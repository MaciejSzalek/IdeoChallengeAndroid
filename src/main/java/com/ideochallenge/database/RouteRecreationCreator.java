package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.models.Destination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-27.
 */

public class RouteRecreationCreator {

    public static void createRouteRecreation(Context context){
        DBHelper dbHelper = new DBHelper(context);
        List<Destination> destinationList = new ArrayList<>();

        //Recreation
        destinationList.add
                (new Destination("Recreation", "Fontanna Multimedialna",
                        50.033426, 22.002541,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Park Papieski",
                        50.01546, 22.020462,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Park Kultury i Wypoczynku",
                        50.02671, 21.99999,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Bulwary",
                        50.027821, 21.998671,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Al. Pod Kasztanami",
                        50.032452, 21.999411,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Rezerwat Lisia Gora",
                        50.00986, 21.98625,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Żwirownia",
                        50.01294, 21.999701,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Ogród Miejski im. Solidarności",
                        50.03038, 21.99452,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Ogrody Bernardyńskie",
                        50.039749, 21.99966,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Park Papieski",
                        50.01546, 22.020462,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Skwer Różanka",
                        50.036707, 22.002321,
                        10, 2));
        destinationList.add
                (new Destination("Recreation", "Elektrownia wodna / Zapora Rzeszów",
                        50.019881, 22.002658,
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
