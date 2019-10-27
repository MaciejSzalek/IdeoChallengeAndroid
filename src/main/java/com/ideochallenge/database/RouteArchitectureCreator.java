package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.models.Destination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-27.
 */

public class RouteArchitectureCreator {

    public static void createRouteArchitectur(Context context){

        DBHelper dbHelper = new DBHelper(context);
        List<Destination> destinationList = new ArrayList<>();

        // ARCHITECTURE
        destinationList.add
                (new Destination("Architecture", "Letni Pałac Lubomirskich",
                        50.03388, 22.00168,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Ratusz / Rynek",
                        50.03736, 22.00398,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Rzeszowskie Piwnice",
                        50.037739, 22.004141,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Okrągła Kładka dla Pieszych",
                        50.037857, 22.002773,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Wille Secesyjne",
                        50.033669, 22.00095,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Wille Secesyjne",
                        50.033669, 22.00095,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Kolorowy Most Gabriela Narutowicza",
                        50.03441, 22.01325,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Dworzec kolejowy Rzeszów Główny",
                        50.04257, 22.00706,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Dworzec kolejowy Rzeszów Główny",
                        50.04257, 22.00706,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Plac Cichociemnych",
                        50.037, 22.00701,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Most im. Tadeusza Mazowieckiego",
                        50.06022, 22.01651,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Wille dyrektorskie",
                        50.02544, 21.98666,
                        10, 2));
        destinationList.add
                (new Destination("Architecture", "Schron przeciwatomowy \"MARYSIEŃKA\"",
                        50.037265, 22.052780,
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
