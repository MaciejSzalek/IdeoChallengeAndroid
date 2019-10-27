package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.models.Destination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-07.
 */
public class RouteSportCreator {

    public static void createRouteSport(Context context) throws SQLException {

        DBHelper dbHelper = new DBHelper(context);
        List<Destination> destinationList = new ArrayList<>();

        //SPORT
        destinationList.add
                (new Destination("Sport", "Centrum Wspinaczkowe \"BALTORO\"",
                        50.06248, 21.97834,
                        10, 2));
        destinationList.add
                (new Destination("Sport", "Tor Motocrossowy Lamba Mx",
                        50.068429, 22.063938,
                        10, 2));
        destinationList.add
                (new Destination("Sport", "Aeroklub Rzeszowski",
                        50.11533, 22.02792,
                        10, 2));
        destinationList.add
                (new Destination("Sport", "Studio Krav Magi",
                        50.035265, 22.017588,
                        10, 2));
        destinationList.add
                (new Destination("Sport", "Stadion Miejski \"Stal\"",
                        50.02131, 21.99578,
                        10, 2));
        destinationList.add
                (new Destination("Sport", "Street workout",
                        50.027821, 21.998671,
                        10, 2));
        destinationList.add
                (new Destination("Sport", "Rzeszowski Klub Badmintona",
                        50.05986, 22.00518,
                        10, 2));
        destinationList.add
                (new Destination("Sport", "Rzeszowski Klub Taekwon-do",
                        50.03359, 21.99477,
                        10, 2));
        destinationList.add
                (new Destination("Sport", "Power Jump Fitness",
                        49.995454, 21.990846,
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
