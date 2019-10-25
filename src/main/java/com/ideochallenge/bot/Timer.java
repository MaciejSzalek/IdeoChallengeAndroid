package com.ideochallenge.bot;

import android.os.Handler;
import android.os.SystemClock;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Maciej Szalek on 2019-10-24.
 */
/* Timer scale :
    1 real seconds = 1 app minute
    1 real minute = 1 app hour
 */
public class Timer {

    private Handler timerHandler = new Handler();
    private static EventBus eventBus = EventBus.getDefault();

    private Long timeInMillisecond;
    private Long timeSwapBuff = 0L;
    private Long updatedTime = 0L;
    private Long startTime = 0L;
    private int clock24 = 24;
    private int scale = 1;
    private int seconds;
    private int minutes = 0;
    // 1 virtual hour = 60000 milliseconds
    private Long converter = 60000L;

    public Timer() {
        eventBus.register(this);
    }

    private Runnable updatedTimerThread = new Runnable() {
        @Override
        public void run() {

            timeInMillisecond = (SystemClock.uptimeMillis() - startTime) * scale;
            //application virtual time start 12:00 o'clock
            updatedTime = timeSwapBuff + timeInMillisecond;

            seconds = (int) (updatedTime/1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            //hours = minutes / 60;
            minutes = minutes % 60;

            //convert real time to virtual
            Integer virtualHours = minutes;
            Integer virtualMinutes = seconds;

            eventBus.post(new Events.TimerEvent(virtualHours, virtualMinutes));
            timerHandler.postDelayed(this, 0);
        }
    };

    @Subscribe
    public void getTimerScale(Events.TimerScale timerScale){
        stopTimer();
        scale = timerScale.getScale();
        startTimer();
    }

    @Subscribe
    public void getTimerEvent(Events.TimerEvent timerEvent){
        Integer hour = timerEvent.getHours();
        if(hour == clock24){
            restartTimer();
        }
    }

    public void startTimer(){
        if(timeSwapBuff == 0){
            timeSwapBuff = 15 * converter;
        }
        startTime = SystemClock.uptimeMillis();
        timerHandler.postDelayed(updatedTimerThread, 0);
    }

    public void stopTimer(){
        timeSwapBuff += timeInMillisecond;
        timerHandler.removeCallbacks(updatedTimerThread);
    }

    private void restartTimer(){
        timerHandler.removeCallbacks(updatedTimerThread);
        SystemClock.setCurrentTimeMillis(0);
        timeSwapBuff = converter;
        timeInMillisecond = 0L;
        startTimer();
    }
}
