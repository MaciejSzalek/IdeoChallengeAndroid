package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.model.Destination;

import java.sql.SQLException;

/**
 * Created by Maciej Szałek on 2019-10-07.
 */
public class HistoryTrack {


    public static void createHistoryTrack(Context context) throws SQLException {

        DBHelper dbHelper = new DBHelper(context);

        Destination des1 = new Destination("Zamek Lubomirskich", "History", 50.032397, 22.000574);
        Destination des2 = new Destination("Pałac Lubomirskich", "History", 50.033706, 22.002530);
        Destination des3 = new Destination("Pomnik S.Konarskiego", "History", 50.035207, 22.001033);
        Destination des4 = new Destination("Ratusz", "History", 50.037478, 22.004133);

        dbHelper.createNewDestination(des1);
        dbHelper.createNewDestination(des2);
        dbHelper.createNewDestination(des3);
        dbHelper.createNewDestination(des4);
    }

}
