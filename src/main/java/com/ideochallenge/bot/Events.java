package com.ideochallenge.bot;

/**
 * Created by Maciej Szalek on 2019-10-24.
 */

public class Events {

    public static class BotEvent {
        private Integer count = 0;
        public BotEvent(Integer count){
            this.count = count;
        }
        public Integer getCount() {
            return count;
        }
    }

    public static class TimerEvent {
        private Integer hours;
        private Integer minutes;
        TimerEvent(Integer hours, Integer minutes){
            this.hours = hours;
            this.minutes = minutes;
        }
        public Integer getHours(){
            return hours;
        }
        public Integer getMinutes(){
            return minutes;
        }

    }
    public static class TimerScale {
        private Integer scale;
        public TimerScale(Integer scale){ this.scale = scale; }
        Integer getScale() {
            return scale;
        }
    }
}
