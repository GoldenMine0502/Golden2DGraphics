package com.GoldenMine.utility;

public class Interval {
    int wait;
    int interval;

    int waitTick;
    int intervalTick;

    boolean completed = false;

    public Interval(int wait, int interval) {
        this.wait = wait;
        this.interval = interval;
    }

    public boolean addTick() {
        intervalTick++;
        //completed = intervalTick >= interval;

        return intervalTick >= interval;
    }

    public boolean addWait() {
        waitTick++;

        completed = waitTick >= wait;

        return completed;
    }

    public void initalize() {
        intervalTick = 0;
    }

    public int getIntervalTick() {
        return intervalTick;
    }

    public int getWaitTick() {
        return waitTick;
    }


    public int getInterval() {
        return interval;
    }

    public int getWait() {
        return wait;
    }

    public int getWaitPercent() {
        return (int) Math.round((double)waitTick/wait*100);
    }

    public int getIntervalPercent() {
        return (int) Math.round((double)intervalTick/interval*100);
    }

    public boolean getCompletedWait() {
        return completed;
    }
}
