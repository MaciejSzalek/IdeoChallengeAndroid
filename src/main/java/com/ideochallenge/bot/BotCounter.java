package com.ideochallenge.bot;

/**
 * Created by Maciej Szalek on 2019-10-23.
 */

public class BotCounter {
    private static int botCount = 0;

    public static int addBot() {
        return botCount += 1;
    }

    public static int subtractBot(){
        if(botCount == 0){
            return 0;
        }else{
            return botCount -= 1;
        }
    }

    public static int getBotCount(){
        return botCount;
    }
}
