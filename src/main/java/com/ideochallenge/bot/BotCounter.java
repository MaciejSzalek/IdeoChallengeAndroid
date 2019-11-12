package com.ideochallenge.bot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Maciej Szalek on 2019-10-23.
 */

public class BotCounter {

    private static int botCount = 0;
    private static EventBus eventBus = EventBus.getDefault();

    private BotCounter(){
        eventBus.register(this);
    }

    static int addBot() {
        botCount += 1;
        eventBus.post(new Events.BotEvent(botCount));
        return botCount;
    }

    public static int subtractBot(){
        if(botCount == 0){
            eventBus.post(new Events.BotEvent(botCount));
            return 0;
        }else{
            eventBus.post(new Events.BotEvent(botCount));
            return botCount -= 1;
        }
    }

    @Subscribe
    public void getBotEvent(Events.BotEvent botEvent){
        //botCount = botEvent.getCount();
    }
}
