package com.ideochallenge.bot;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Maciej Szalek on 2019-10-23.
 */

public class BotManager implements Runnable {

    private BotCreator botCreator;
    private Context context;
    private GoogleMap map;

    int i = 1;

    public BotManager(Context context, GoogleMap map){
        this.context = context;
        this.map = map;
    }

    @Override
    public void run() {
        test();
    }

    private void manageBot() {
        botCreator = new BotCreator(context, map);
        botCreator.createNewBot();
    }

    private void test(){
        while(i == 1){
            Log.d("BOT OUTER LOOP", "in progress");
            if (BotCounter.getBotCount() < 5){
                BotCounter.addBot();
                botCreator = new BotCreator(context, map);
                botCreator.createNewBot();
                Log.d("BOT CREATOR: ", "loop in progress");
            }
        }
    }
}
