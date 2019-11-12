package com.ideochallenge.bot;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Maciej Szalek on 2019-10-23.
 */

public class BotManager {

    private Context context;
    private GoogleMap map;

    public BotManager(Context context, GoogleMap map){
        this.context = context;
        this.map = map;
    }

    public void manageBot(Integer botCount, Integer hours) {
        BotCreator botCreator;
        if(hours >= 9 && hours < 16){
            if(botCount < 5){
                BotCounter.addBot();
                botCreator = new BotCreator(context, map);
                botCreator.createNewBot();
            }
        } else if(hours >= 16 && hours < 22) {
            if(botCount < 8){
                BotCounter.addBot();
                botCreator = new BotCreator(context, map);
                botCreator.createNewBot();
            }
        } else if(hours >=22 && hours < 9) {
            if(botCount < 3){
                BotCounter.addBot();
                botCreator = new BotCreator(context, map);
                botCreator.createNewBot();
            }
        }
    }
}
