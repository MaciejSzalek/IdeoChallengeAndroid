package com.ideochallenge.bot;

import com.google.android.gms.maps.GoogleMap;

import java.util.Random;

/**
 * Created by Maciej Szalek on 2019-10-14.
 */

public class BotCreator {

    private enum Routes {
        history,
        food,
        hipster,
        fun
    }

    public static void createNewBot(GoogleMap mMap) {

    }
    private void randomRout() {
        int num = 0;
    }

    private int randomPoints(){
        int min = 0;
        int max = 5;
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
