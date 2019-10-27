package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.models.Destination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-27.
 */

public class RouteCultureArtCreator {

    public static void createRouteCultureArt(Context context){

        DBHelper dbHelper = new DBHelper(context);
        List<Destination> destinationList = new ArrayList<>();

        destinationList.add
                (new Destination("Culture and Art", "Teatr im. Wandy Siemaszkowej",
                        50.03906, 21.99974,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Filharmonia Podkarpacka",
                        50.03329, 22.00464,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Teatr Maska",
                        50.03777, 22.00675,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Synagoga Staromiejska/Biuro Wystaw Artystycznych",
                        50.0381, 22.00729,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Galeria Fotografii DWA PLANY",
                        50.04887, 21.97648,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Aparat Caffe (Fotografie Edwarda Janusza)",
                        50.039261, 22.00049,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Dom Polonii",
                        50.03781, 22.004938,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Kino Zorza",
                        50.03494, 22.00083,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Teatr im. Wandy Siemaszkowej",
                        50.03906, 21.99974,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Dagart Galerie SZTUKA DO KWADRATU",
                        50.028255, 22.015542,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "SZAJNA GALERIA",
                        50.039240, 21.999637,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Mural z samolotem Łoś",
                        50.039371, 22.002859,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Mural dziewczynka w słuchawkach",
                        50.034161, 21.994619,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Mural dziewczynka w słuchawkach",
                        50.034161, 21.994619,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Mural z Tadeuszem Nalepą",
                        50.034962, 21.99571,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Mural z fotografem Edwardem Januszem",
                        50.034672, 21.998079,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Mural z saksofonem",
                        50.03817, 22.009661,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Mural Stanisława Wyspiańskiego",
                        50.038952, 21.981211,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Mural \"W samo południe\"",
                        50.034939, 21.998289,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Mural Ireny Sendlerowej",
                        50.038109, 22.00588,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Mural z Garym Cooperem",
                        50.034672, 21.998079,
                        10, 2));
        destinationList.add
                (new Destination("Culture and Art", "Dom Kultury WSK/ dawne Kino Mewa",
                        50.02325, 21.98571,
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
