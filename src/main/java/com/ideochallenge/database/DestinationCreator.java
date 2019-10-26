package com.ideochallenge.database;

import android.content.Context;

import com.ideochallenge.models.Destination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciej Szalek on 2019-10-07.
 */
public class DestinationCreator {

    public static void createDestinationDatabase(Context context) throws SQLException {

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

        //CULTURE and ART
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


        //Monuments and sculptures
        destinationList.add
                (new Destination("Monuments and sculptures", "Pomnik Czynu Rewolucyjnego",
                        50.04056, 21.99948,
                        10, 2));
        destinationList.add
                (new Destination("Monuments and sculptures", "Pomnik Urwis z procą",
                        50.03545, 22.003019,
                        10, 2));
        destinationList.add
                (new Destination("Monuments and sculptures", "Pomnik Tadeusza Nalepy",
                        50.0372, 22.00185,
                        10, 2));
        destinationList.add
                (new Destination("Monuments and sculptures", "Pomnik Sybiraków",
                        50.05009, 21.97694,
                        10, 2));

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
    }
}
